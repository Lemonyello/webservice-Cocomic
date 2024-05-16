package webserviceThuvien.AccessManager;

import java.sql.Connection;
import java.util.ArrayList;

import org.json.JSONObject;

import webserviceThuvien.MySQL.AccessToDB;
import webserviceThuvien.MySQL.ConnectToDB;
import webserviceThuvien.model.Comic;
import webserviceThuvien.model.ComicChapter;
import webserviceThuvien.model.ComicPage;
import webserviceThuvien.model.User;

public class AccessManage {

    public JSONObject register(User u) throws Exception {
        return new AccessToDB().registerAccount(new ConnectToDB().getConnection(), u);
    }

    public JSONObject signIn(User u) throws Exception {
        return new AccessToDB().signIn(new ConnectToDB().getConnection(), u);
    }

    public JSONObject changePassword(User u, String newPass) throws Exception {
        return new AccessToDB().changePassword(new ConnectToDB().getConnection(), u, newPass);
    }

    public ArrayList<Comic> searchComicByName(String name) throws Exception {
        return new AccessToDB().searchComicByName(new ConnectToDB().getConnection(), name);
    }

    public ArrayList<Comic> searchComicByCategory(int cateId, int orderBy) throws Exception {
        return new AccessToDB().searchComicByCategory(new ConnectToDB().getConnection(), cateId, orderBy);
    }

    public JSONObject followBook(String userId, String bookId) throws Exception {
        return new AccessToDB().followBook(new ConnectToDB().getConnection(), userId, bookId);
    }

    public JSONObject unfollowBook(String userId, String bookId) throws Exception {
        return new AccessToDB().unfollowBook(new ConnectToDB().getConnection(), userId, bookId);
    }

    public ArrayList<Comic> getUserReadHistory(String userId) throws Exception {
        return new AccessToDB().getReadHistory(new ConnectToDB().getConnection(), userId);
    }

    public ArrayList<Comic> getComicList(int size, int page) throws Exception {
        return new AccessToDB().getComicList(new ConnectToDB().getConnection(), size, page);
    }

    public Comic getComicInfo(String bookId) throws Exception {
        return new AccessToDB().getComicInfo(new ConnectToDB().getConnection(), bookId);
    }

    public ArrayList<ComicChapter> getComicChapterList(String bookId) throws Exception  {
        return new AccessToDB().getComicChapterList(new ConnectToDB().getConnection(), bookId);
    }

    public ArrayList<ComicPage> getComicChapterGuest(String chapterId) throws Exception {
        return new AccessToDB().getComicChapterGuest(new ConnectToDB().getConnection(), chapterId);
    }

    public ArrayList<ComicPage> getComicChapterUser(String chapterId, String userId) throws Exception {
        return new AccessToDB().getComicChapterUser(new ConnectToDB().getConnection(), chapterId, userId);
    }
    
    public JSONObject CMS_createNewComic(Comic comic) throws Exception {
        return new AccessToDB().CMS_createNewComic(new ConnectToDB().getConnection(), comic);
    }

    public JSONObject CMS_createNewChapter(ComicChapter chapter) throws Exception {
        return new AccessToDB().CMS_createNewChapter(new ConnectToDB().getConnection(), chapter);
    }

    public JSONObject CSM_deleteBook(String bookId) throws Exception {
        return new AccessToDB().CMS_deleteBook(new ConnectToDB().getConnection(), bookId);
    }

    public ArrayList<User> CMS_getUserList(int size, int page) throws Exception {
        return new AccessToDB().CMS_getUserList(new ConnectToDB().getConnection(), size, page);
    }

    public ArrayList<User> CMS_searchUser(String userId) throws Exception {
        return new AccessToDB().CMS_searchUser(new ConnectToDB().getConnection(), userId);
    }
}