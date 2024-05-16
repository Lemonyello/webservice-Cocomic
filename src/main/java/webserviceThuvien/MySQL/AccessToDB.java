package webserviceThuvien.MySQL;

import java.sql.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import webserviceThuvien.model.Comic;
import webserviceThuvien.model.ComicChapter;
import webserviceThuvien.model.ComicPage;
import webserviceThuvien.model.User;

public class AccessToDB {

	public JSONObject registerAccount(Connection conn, User u) throws SQLException {
		String storedProcedureCall = "{call dbo.SP_Account_Register(?, ?, ?, ?, ?, ?)}";

		CallableStatement cStmt = conn.prepareCall(storedProcedureCall);

		// Set input parameters value.
		cStmt.setString(1, u.getAccountName());
		cStmt.setString(2, u.getPassword());
		cStmt.setString(3, u.getNickName());

		cStmt.registerOutParameter(4, java.sql.Types.NVARCHAR);
		cStmt.registerOutParameter(5, java.sql.Types.INTEGER);
		cStmt.registerOutParameter(6, java.sql.Types.NVARCHAR);

		// Execute stored procedure.
		cStmt.executeUpdate();
		JSONObject json = new JSONObject();
		json.put("userId", cStmt.getInt(5));
		json.put("message", cStmt.getString(6));

		// Do not forget close Callable Statement and db connection object.
		cStmt.close();
		conn.close();
		return json;
	}

	public JSONObject signIn (Connection conn, User u) throws SQLException {
		String storedProcedureCall = "{call dbo.SP_Account_Authenticate" +
				"(1, '', ?, ?, '', '', '8.8.8.8', '', '', '', ?, '', ?)}";

		CallableStatement cStmt = conn.prepareCall(storedProcedureCall);

		// Set input parameters value.
		cStmt.setString(1, u.getAccountName());
		cStmt.setString(2, u.getPassword());

		cStmt.registerOutParameter(3, java.sql.Types.INTEGER);
		cStmt.registerOutParameter(4, java.sql.Types.NVARCHAR);

		// Execute stored procedure.
		cStmt.executeUpdate();
		JSONObject json = new JSONObject();
		json.put("id", cStmt.getInt(3));
		json.put("message", cStmt.getString(4));

		// Do not forget close Callable Statement and db connection object.
		cStmt.close();
		conn.close();
		return json;
	}

	public JSONObject changePassword(Connection con, User u, String newPass) throws SQLException {
		JSONObject jo = new JSONObject();
		CallableStatement cs = con.prepareCall("{call SP_Account_Set_Password(?, ?, ?, ?, ?)}");
		cs.setString(1, u.getAccountName());
		cs.setString(2, u.getPassword());
		cs.setString(3, newPass);
		cs.registerOutParameter(4, Types.INTEGER);
		cs.registerOutParameter(5, Types.NVARCHAR);
		cs.executeUpdate();
		jo.put("id", cs.getInt(4));
		jo.put("message", cs.getString(5));
		cs.close();
		con.close();
		return jo;
	}

	// TODO test sign in, encrypt password, test search by name with space
	// TODO test APIs for CMS: create book and chapter, delete book, list user, search user
	// TODO API for ranking, update comic and chapter, delete chapter, get category list
	// TODO consider comment and settings
	// TODO filter out old records of WebtoonDB
	// TODO FE CMS add image
	// TODO deploy to Tomcat
	// TODO mobile app
	// TODO API doc

	public JSONObject followBook(Connection con, String userId, String bookId) throws SQLException {
		CallableStatement cs = con.prepareCall("{call SP_User_FollowBook_Create(?, ?, ?, ?)}");
		cs.setString(1, userId);
		cs.setString(2, bookId);
		cs.registerOutParameter(3, Types.INTEGER);
		cs.registerOutParameter(4, Types.NVARCHAR);
		cs.executeUpdate();
		JSONObject jo = new JSONObject();
		jo.put("id", cs.getInt(3));
		jo.put("message", cs.getString(4));
		cs.close();
		con.close();
		return jo;
	}

	public JSONObject unfollowBook(Connection con, String userId, String bookId) throws SQLException {
		CallableStatement cs = con.prepareCall("{call SP_User_FollowBook_UnFollow(?, ?, ?, ?)}");
		cs.setString(1, userId);
		cs.setString(2, bookId);
		cs.registerOutParameter(3, Types.INTEGER);
		cs.registerOutParameter(4, Types.NVARCHAR);
		cs.executeUpdate();
		JSONObject jo = new JSONObject();
		jo.put("id", cs.getInt(3));
		jo.put("message", cs.getString(4));
		cs.close();
		con.close();
		return jo;
	}

	public ArrayList<Comic> getComicList(Connection con,int size, int page) throws SQLException {
		ArrayList<Comic> comics = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call SP_CMS_Book_GetList(NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?, ?, NULL)}");
		cs.setInt(1, size);
		cs.setInt(2, page);
		cs.executeQuery();
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
			Comic comic = new Comic();
			comic.setId(rs.getString("BookId"));
			comic.setName(rs.getString("BookName"));
			comic.setCategory(rs.getString("CategoryName"));
			comic.setDescription(rs.getString("BookDescription"));
			// TODO filter out link index
			comic.setImage(rs.getString("ImgUrl").substring(rs.getString("ImgUrl").lastIndexOf("http")));
			comics.add(comic);
		}
		cs.close();
		con.close();
		return comics;
	}

	public ArrayList<Comic> getReadHistory(Connection con, String userId) throws SQLException {
		ArrayList<Comic> comics = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call dbo.SP_UserBook_Read_History_GetList(?, -1)}");
		cs.setString(1, userId);
		cs.executeQuery();
		ResultSet rs = cs.getResultSet();
		while(rs.next()) {
			Comic comic = new Comic();
			comic.setId(rs.getString("BookId"));
			comic.setName(rs.getString("BookName"));
			// TODO filter out previous link
			comic.setImage(rs.getString("imgUrl"));
			comics.add(comic);
		}
		cs.close();
		con.close();
		return comics;
	}

	public ArrayList<Comic> searchComicByName(Connection con, String name) throws SQLException{
		ArrayList<Comic> comics = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call SP_Book_Search(NULL, ?, 0)}");
		cs.setString(1, name);
		cs.executeQuery();
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
			Comic comic = new Comic();
			comic.setId(rs.getString("BookId"));
			comic.setName(rs.getString("BookName"));
			comic.setCategory(rs.getString("CategoryName"));
			// TODO filter out previous link
			comic.setImage(rs.getString("BookImage"));
			comic.setFollowNo(rs.getInt("FollowNo"));
			comic.setViewNo(rs.getInt("ViewNo"));
			comics.add(comic);
		}
		cs.close();
		con.close();
		return comics;
	}

	public ArrayList<Comic> searchComicByCategory(Connection con, int cateId, int orderBy) throws SQLException {
		ArrayList<Comic> comics = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call SP_Book_GetList_ByCategoryID(?, ?)}");
		cs.setInt(1, cateId);
		cs.setInt(2, orderBy);
		cs.executeQuery();
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
			Comic comic = new Comic();
			comic.setId(rs.getString("BookId"));
			comic.setName(rs.getString("BookName"));
			// TODO filter out previous link
			comic.setImage(rs.getString("imgUrl"));
			comic.setFollowNo(rs.getInt("FollowNo"));
			comic.setViewNo(rs.getInt("ViewNo"));
			comics.add(comic);
		}
		cs.close();
		con.close();
		return comics;
	}

	public Comic getComicInfo(Connection con, String id) throws SQLException {
		Comic comic = new Comic();
		CallableStatement cs = con.prepareCall("{call dbo.SP_User_Book_GetInfo_Detail(?, '')}");
		cs.setString(1, id);
		cs.executeQuery();
		ResultSet rs = cs.getResultSet();
		rs.next();
		comic.setId(rs.getString("BookId"));
		comic.setName(rs.getString("BookName"));
		comic.setDescription(rs.getString("BookDescription"));
		comic.setCategory(rs.getString("CategoryName"));
		comic.setLastUpdateTime(rs.getDate("lastUpdateTime"));
		comic.setFollowNo(rs.getInt("FollowNo"));
		comic.setViewNo(rs.getInt("ViewNo"));
		// TODO filter out previous link
		comic.setImage(rs.getString("BookImage"));
		cs.close();
		con.close();
		return comic;
	}

	public ArrayList<ComicChapter> getComicChapterList(Connection con, String bookId) throws SQLException {
		ArrayList<ComicChapter> chapters = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call SP_CMS_BookChapter_GetList(?, '', '', 2000, 1, '')}");
		cs.setString(1, bookId);
		cs.executeQuery();
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
			ComicChapter chapter = new ComicChapter();
			chapter.setId(rs.getString("ChapterId"));
			chapters.add(chapter);
		}
		cs.close();
		con.close();
		return chapters;
	}

	public ArrayList<ComicPage> getComicChapterGuest(Connection con, String chapterId) throws SQLException {
		ArrayList<ComicPage> pages = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call SP_CMS_BookChapterDetail_GetById(?)}");
		cs.setString(1, chapterId);
		cs.executeQuery();
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
			ComicPage page = new ComicPage();
			page.setPage(rs.getInt("Page"));
			// TODO filter out previous link
			page.setPageImgUrl(rs.getString("PageImgUrl"));
			pages.add(page);
		}
		cs.close();
		con.close();
		return pages;
	}

	public ArrayList<ComicPage> getComicChapterUser(Connection con, String chapterId, String userId) throws SQLException {
		ArrayList<ComicPage> pages = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call SP_User_Book_GetList_ChapterDetail(?, ?, '', 1, 10, '')}");
		cs.setString(1, chapterId);
		cs.setString(2, userId);
		cs.executeQuery();
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
			ComicPage page = new ComicPage();
			page.setPage(rs.getInt("Page"));
			// TODO filter out previous link
			page.setPageImgUrl(rs.getString("PageImgUrl"));
			pages.add(page);
		}
		cs.close();
		con.close();
		return pages;
	}

	public JSONObject CMS_createNewComic(Connection con, Comic comic) throws SQLException {
		CallableStatement cs = con.prepareCall(
				"{call SP_CMS_Book_CreateNew" +
						"(?, ?, 0, 1, 0, 16, 'VTC', ?, 0, ?, ?, NULL, ?, 1, ?, ?)}");
		cs.setString(1, comic.getName());
		cs.setString(2, comic.getDescription());
		cs.setString(3, comic.getTags());
		cs.setString(4, comic.getCategory());
		cs.setString(5, comic.getImage());
		cs.registerOutParameter(6, Types.OTHER);
		cs.registerOutParameter(7, Types.INTEGER);
		cs.registerOutParameter(8, Types.NVARCHAR);
		cs.executeUpdate();
		JSONObject jo = new JSONObject();
		jo.put("id", cs.getInt(7));
		jo.put("message", cs.getString(8));
		cs.close();
		con.close();
		return jo;
	}

	public JSONObject CMS_createNewChapter(Connection con, ComicChapter chapter) throws SQLException {
		JSONObject jo = new JSONObject();
		JSONArray pages = new JSONArray(chapter.getPages());
		CallableStatement cs = con.prepareCall("{call SP_CMS_BookChapter_Create" +
				"(?, ?, 0, 1, ?, 0, '', NULL, ?, ?, NULL, NULL, NULL, ?, ?, ?)}");
		cs.setString(1, chapter.getBookId());
		cs.setString(2, chapter.getName());
		cs.setDate(3, (Date) chapter.getPublishDate());
		cs.setString(4, pages.toString());
		cs.setInt(5, chapter.getSortOrder());
		cs.registerOutParameter(6, Types.INTEGER);
		cs.registerOutParameter(7, Types.NVARCHAR);
		cs.executeUpdate();
		jo.put("id", cs.getInt(1));
		jo.put("message", cs.getString(2));
		cs.close();
		con.close();
		return jo;
	}

	public JSONObject CMS_deleteBook(Connection con, String bookId) throws SQLException {
		JSONObject jo = new JSONObject();
		CallableStatement cs = con.prepareCall("{call SP_CMS_Book_Delete(?, '', '')}");
		cs.setString(1, bookId);
		cs.registerOutParameter(2, Types.INTEGER);
		cs.registerOutParameter(3, Types.NVARCHAR);
		cs.executeUpdate();
		jo.put("id", cs.getInt(2));
		jo.put("message", cs.getString(3));
		cs.close();
		con.close();
		return jo;
	}

	public ArrayList<User> CMS_getUserList(Connection con, int size, int page) throws SQLException{
		ArrayList<User> users = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call SP_CMS_Account_GetList(" +
				"NULL, NULL, NULL, ?, ?, '')}");
		cs.setInt(1, size);
		cs.setInt(2, page);
		cs.executeQuery();
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
			User u = new User();
			u.setId(rs.getString(1));
			u.setAccountName(rs.getString(2));
			u.setNickName(rs.getString(3));
			users.add(u);
		}
		cs.close();
		con.close();
		return users;
	}

	public ArrayList<User> CMS_searchUser(Connection con, String userId) throws SQLException {
		ArrayList<User> users = new ArrayList<>();
		CallableStatement cs = con.prepareCall("{call SP_Account_GetLInfo(?)}");
		cs.setString(1, userId);
		cs.executeQuery();
		ResultSet rs = cs.getResultSet();
		while (rs.next()) {
			User u = new User();
			u.setId(rs.getString(1));
			u.setAccountName(rs.getString(2));
			u.setNickName(rs.getString(3));
			users.add(u);
		}
		cs.close();
		con.close();
		return users;
	}
}
