package flint.business.tag.dao.impl;

import static kiss.util.Helper.$;

import java.util.ArrayList;
import java.util.List;

import kiss.util.Q;

import org.springframework.stereotype.Repository;

import entity.Tag;
import flint.base.BaseDAOImpl;
import flint.business.tag.dao.TagDAO;

@Repository
public class TagDAOImpl extends BaseDAOImpl<Tag, Long> implements TagDAO {

	@Override
	public void save(String tag, long objectId, byte group) {
		/* 先删除原有统计数据 */
		this.drop(objectId, group);
		if (!Q.isBlank(tag)) {
			String[] tp = tag.split(",");
			List<Object[]> parameters = new ArrayList<Object[]>();
			String[] ids = new String[tp.length];
			int i = 0;
			/* 插入新数据 */
			for (String p : tp) {
				p = p.trim();
				long id = this.getLong("select id from TB_TAG where name=?", p);
				if (id == 0) {
					Tag t = new Tag();
					t.setName(p);
					t.setDateline(Q.now());
					t.setTagGroup(group);
					id = this.insert(t);
				}
				parameters.add(new Object[] { objectId, group, id });
				ids[i++] = String.valueOf(id);
			}
			this.batchUpdate(
					"insert into TB_ITEM_TAG(OBJECT_ID,OBJECT_TYPE,TAG_ID) values(?,?,?)",
					parameters);
			this.update("update TB_TAG set counts=counts+1 where id in("
					+ $(ids).join(",") + ")");
		}
	}

	@Override
	public void drop(long objectId, byte group) {
		this.update(
				"update TB_TAG set counts=counts-1 where exists (select 1 from TB_ITEM_TAG where OBJECT_ID=? and  OBJECT_TYPE=? and TB_TAG.id=TB_ITEM_TAG.tag_id)",
				objectId, group);
		this.update(
				"delete from TB_ITEM_TAG  where  OBJECT_ID=? and  OBJECT_TYPE=?",
				objectId, group);
	}

	@Override
	public void delete(String tag) {
		long id = this
				.getLong("select id from TB_TAG where name=?", tag.trim());
		delete(id);
	}

	@Override
	public void delete(long tagId) {
		this.update("delete from TB_ITEM_TAG where tag_id=?", tagId);
		this.update("delete from TB_TAG where id=?", tagId);
	}

	@Override
	public List<Tag> get(byte group) {
		if (group == 0) {
			return this.find("select * from TB_TAG order by counts desc");
		} else {
			return this
					.find("select * from TB_TAG where EXISTS(select 1 FROM TB_ITEM_TAG where TAG_GROUP=? and TB_TAG.ID=TB_ITEM_TAG.TAG_ID) order by counts desc",
							group);
		}
	}
	
	@Override
	public List<Tag> getTop(byte group,long n){
			return this
					.find("select * from TB_TAG where EXISTS(select 1 FROM TB_ITEM_TAG where TAG_GROUP=? and TB_TAG.ID=TB_ITEM_TAG.TAG_ID) order by counts desc limit 0,?",group,n);
	}

	@Override
	public List<Tag> get(long objectId, byte group, boolean sameGroup) {
		String sql="SELECT * FROM TB_TAG where EXISTS(select 1 from (SELECT  TAG_ID from  TB_ITEM_TAG b  where EXISTS( select 1 FROM TB_ITEM_TAG a  where a.OBJECT_ID=? and a.OBJECT_TYPE=? and a.TAG_ID=b.TAG_ID)    #samegroup#) t where t.TAG_ID=TB_TAG.id)";
		if(sameGroup){
			return this.find(sql.replaceFirst("#samegroup#", "and b.OBJECT_TYPE=?"), objectId,group,group);
		}else{
		return this.find(sql.replaceFirst("#samegroup#", ""), objectId,group);
		}
	}
}
