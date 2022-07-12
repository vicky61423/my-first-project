package web.member.servlet;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import web.member.service.MemberService;
import web.member.service.impl.MemberServiceImpl;
import web.member.vo.Member;

@WebServlet("/api/0.01/member/*")
public class memberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	private String pathInfo;
	private String[] infos;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		setHeaders(resp);
		pathInfo = req.getPathInfo();
		infos = pathInfo.split("/");

		JsonObject respObject = new JsonObject();
		Member member = gson.fromJson(req.getReader(), Member.class);

		if ("register".equals(infos[1])) {
			try {
				MemberService service = new MemberServiceImpl();
				Integer status = service.register(member);
				if (status > 0) {
					respObject.addProperty("msg", "success");
				} else {
					respObject.addProperty("msg", "fail");
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
			System.out.println(gson.toJson(respObject));
			resp.getWriter().append(gson.toJson(respObject));
		} else if ("login".equals(infos[1])) {
			try {
				MemberService service = new MemberServiceImpl();
				member = service.login(member);
				if (member != null) {
					respObject.addProperty("msg", "success");
					// Referenced from
					// https://stackoverflow.com/questions/22585970/how-to-add-an-object-as-a-property-of-a-jsonobject-object
					respObject.add("member", new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJsonTree(member));
				} else {
					respObject.addProperty("msg", "fail");
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
			System.out.println(gson.toJson(respObject));
			resp.getWriter().append(gson.toJson(respObject));
		}

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		setHeaders(resp);
		pathInfo = req.getPathInfo();
		infos = pathInfo.split("/");
		JsonObject respObject = new JsonObject();
		Member member = gson.fromJson(req.getReader(), Member.class);
		if ("login".equals(infos[1])) {
			try {
				MemberService service = new MemberServiceImpl();
				Integer status = service.remove(member);
				if (status > 0) {
					respObject.addProperty("msg", "success");
				} else {
					respObject.addProperty("msg", "fail");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			resp.getWriter().append(gson.toJson(respObject));
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		setHeaders(resp);
		pathInfo = req.getPathInfo();
		infos = pathInfo.split("/");
		JsonObject respObject = new JsonObject();
		Member member = gson.fromJson(req.getReader(), Member.class);
		if ("modify".equals(infos[1])) {
			try {
				MemberService service = new MemberServiceImpl();
				if (service.modify(member) > 0) {
					respObject.addProperty("msg", "success");
				} else {
					respObject.addProperty("msg", "fail");
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
			System.out.println(gson.toJson(respObject));
			resp.getWriter().append(gson.toJson(respObject));
		}
	}
	/*
	 * 誇域
	 */
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		setHeaders(resp);
	}
	
	private void setHeaders(HttpServletResponse response) {
		// 重要
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");

		// 重要
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		response.addHeader("Access-Control-Max-Age", "86400");
	}
}
