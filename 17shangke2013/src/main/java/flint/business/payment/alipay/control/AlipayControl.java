package flint.business.payment.alipay.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import flint.business.payment.alipay.services.AlipayService;
import flint.business.payment.alipay.util.AlipayNotify;
import flint.business.payment.alipay.util.UtilDate;
import flint.util.DateHelper;
import flint.web.WebControl;

@Controller
public class AlipayControl extends WebControl {

	@RequestMapping("/payment/alipay.to.html")
	public void _alipay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// //////////////////////////////////请求参数//////////////////////////////////////

		// 必填参数//

		// 请与贵网站订单系统中的唯一订单号匹配
		String out_trade_no = UtilDate.getOrderNum();
		// 订单名称，显示在支付宝收银台里的“商品名称”里，显示在支付宝的交易管理的“商品名称”的列表里。
		String subject = "test";// request.getParameter("subject");
		// 订单描述、订单详细、订单备注，显示在支付宝收银台里的“商品描述”里
		String body = "memo";// request.getParameter("alibody");
		// 订单总金额，显示在支付宝收银台里的“应付总额”里
		String total_fee = "0";// request.getParameter("total_fee");

		// 扩展功能参数——默认支付方式//

		// 默认支付方式，取值见“即时到帐接口”技术文档中的请求参数列表
		String paymethod = "";
		// 默认网银代号，代号列表见“即时到帐接口”技术文档“附录”→“银行列表”
		String defaultbank = "";

		// 扩展功能参数——防钓鱼//

		// 防钓鱼时间戳
		String anti_phishing_key = DateHelper.getDateTimeSequence();
		// 获取客户端的IP地址，建议：编写获取客户端IP地址的程序
		String exter_invoke_ip = request.getRemoteAddr();
		// 注意：
		// 1.请慎重选择是否开启防钓鱼功能
		// 2.exter_invoke_ip、anti_phishing_key一旦被设置过，那么它们就会成为必填参数
		// 3.开启防钓鱼功能后，服务器、本机电脑必须支持远程XML解析，请配置好该环境。
		// 4.建议使用POST方式请求数据
		// 示例：
		// anti_phishing_key = AlipayService.query_timestamp(); //获取防钓鱼时间戳函数
		// exter_invoke_ip = "202.1.1.1";

		// 扩展功能参数——其他///

		// 自定义参数，可存放任何内容（除=、&等特殊字符外），不会显示在页面上
		String extra_common_param = "";
		// 默认买家支付宝账号
		String buyer_email = "";
		// 商品展示地址，要用http:// 格式的完整路径，不允许加?id=123这类自定义参数
		String show_url = "http://127.0.0.1:8080/order/myorder.jsp";

		// 扩展功能参数——分润(若要使用，请按照注释要求的格式赋值)//

		// 提成类型，该值为固定值：10，不需要修改
		String royalty_type = "";
		// 提成信息集
		String royalty_parameters = "";
		// 注意：
		// 与需要结合商户网站自身情况动态获取每笔交易的各分润收款账号、各分润金额、各分润说明。最多只能设置10条
		// 各分润金额的总和须小于等于total_fee
		// 提成信息集格式为：收款方Email_1^金额1^备注1|收款方Email_2^金额2^备注2
		// 示例：
		// royalty_type = "10"
		// royalty_parameters = "111@126.com^0.01^分润备注一|222@126.com^0.01^分润备注二"

		// ////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("payment_type", "1");
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("body", body);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("paymethod", paymethod);
		sParaTemp.put("defaultbank", defaultbank);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		sParaTemp.put("extra_common_param", extra_common_param);
		sParaTemp.put("buyer_email", buyer_email);
		sParaTemp.put("royalty_type", royalty_type);
		sParaTemp.put("royalty_parameters", royalty_parameters);

		/* 直接write定向到支付宝 */
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		ServletOutputStream out = response.getOutputStream();
		// 构造函数，生成请求URL
		out.write(AlipayService.create_direct_pay_by_user(sParaTemp).getBytes("UTF-8"));
		out.flush();
	}

	/* *
	 * 功能：支付宝服务器异步通知页面 版本：3.2 日期：2011-03-17 说明：
	 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	 * 
	 * //***********页面功能说明*********** 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
	 * 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
	 * 该页面调试工具请使用写文本函数logResult，该函数在com.alipay.util文件夹的AlipayNotify.java类文件中
	 * 如果没有收到该页面返回的 success 信息，支付宝会在24小时内按一定的时间策略重发通知
	 * TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
	 * TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
	 * //********************************
	 */
	@RequestMapping("/payment/alipay.notify.html")
	public void _notify(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

		String trade_no = request.getParameter("trade_no"); // 支付宝交易号
		String order_no = request.getParameter("out_trade_no"); // 获取订单号
		String total_fee = request.getParameter("total_fee"); // 获取总金额
		String subject = new String(request.getParameter("subject").getBytes("ISO-8859-1"), "utf-8");// 商品名称、订单名称
		String body = "";
		if (request.getParameter("body") != null) {
			body = new String(request.getParameter("body").getBytes("ISO-8859-1"), "utf-8");// 商品描述、订单备注、描述
		}
		String buyer_email = request.getParameter("buyer_email"); // 买家支付宝账号
		String trade_status = request.getParameter("trade_status"); // 交易状态

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		if (AlipayNotify.verify(params)) {// 验证成功
			// ////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

			if (trade_status.equals("TRADE_FINISHED")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				// 注意：
				// 该种交易状态只在两种情况下出现
				// 1、开通了普通即时到账，买家付款成功后。
				// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				// 注意：
				// 该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
			}

			// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

			// out.println("success"); //请不要修改或删除——

			// ////////////////////////////////////////////////////////////////////////////////////////
		} else {// 验证失败
			// out.println("fail");
		}
	}

	/* *
	 * 功能：支付宝页面跳转同步通知页面 版本：3.2 日期：2011-03-17 说明：
	 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	 * 
	 * //***********页面功能说明*********** 该页面可在本机电脑测试 可放入HTML等美化页面的代码、商户业务逻辑程序代码
	 * TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
	 * TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
	 * //********************************
	 */
	@RequestMapping("/payment/alipay.return.html")
	public void _return(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// 获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

		String trade_no = request.getParameter("trade_no"); // 支付宝交易号
		String order_no = request.getParameter("out_trade_no"); // 获取订单号
		String total_fee = request.getParameter("total_fee"); // 获取总金额
		String subject = new String(request.getParameter("subject").getBytes("ISO-8859-1"), "utf-8");// 商品名称、订单名称
		String body = "";
		if (request.getParameter("body") != null) {
			body = new String(request.getParameter("body").getBytes("ISO-8859-1"), "utf-8");// 商品描述、订单备注、描述
		}
		String buyer_email = request.getParameter("buyer_email"); // 买家支付宝账号
		String trade_status = request.getParameter("trade_status"); // 交易状态

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);

		if (verify_result) {// 验证成功
			// ////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序
				
				
			}
		    
			
			// 该页面可做页面美工编辑
			// out.println("验证成功<br />");
			// out.println("trade_no=" + trade_no);

			// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			// ////////////////////////////////////////////////////////////////////////////////////////
		} else {
			// 该页面可做页面美工编辑
			// out.println("验证失败");
		}
	}
}
