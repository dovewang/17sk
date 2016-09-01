package flint.business.tag.dao;

import java.util.List;

import entity.Tag;
import flint.base.BaseDAO;

public interface TagDAO extends BaseDAO<Tag, Long> {
	/**
	 * 
	 * @param tag
	 *            标签 ，以逗号分隔的标签集合
	 * @param objectId
	 * @param group
	 *            使用与收藏标签相同的类型
	 * @see flint.business.constant.CollectConst
	 */
	void save(String tag, long objectId, byte group);

	/**
	 * 删除
	 * 
	 * @param objectId
	 * @param group
	 * @see flint.business.constant.CollectConst
	 */
	void drop(long objectId, byte group);

	/**
	 * 删除某个标签
	 * 
	 * @param tag
	 */
	void delete(String tag);

	/**
	 * 按ID删除某个标签
	 * 
	 * @param tagId
	 */
	void delete(long tagId);

	/**
	 * 取得分类下所有的标签
	 * 
	 * @param group
	 *            为0时返回系统的所有标签
	 * @return
	 */
	List<Tag> get(byte group);

	/**
	 * 
	 * 取得该对象下的关联性标签
	 * 
	 * @param objectId
	 * @param sameGroup
	 * @param group
	 * @return
	 */
	List<Tag> get(long objectId, byte group, boolean sameGroup);
	
	/**
	 * 取得分类下前N条标签
	 * 
	 * @param group
	 *            为0时返回系统的所有标签
	 * @return
	 */
	List<Tag> getTop(byte group, long n);

}
