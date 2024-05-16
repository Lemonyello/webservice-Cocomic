package webserviceThuvien.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.*;

import webserviceThuvien.AccessManager.AccessManage;
import webserviceThuvien.model.*;

@Path("/Service")
public class Service {

    @GET
    @Path("POJOTest")
    @Produces("application/json")
    public Response testPOJO() {
        Comic comic = new Comic();
        comic.setName("abc");
        comic.setId("123");
        return Response.ok(comic).build();
    }

    @POST
    @Path("register")
    @Consumes("application/json")
    @Produces("application/json")
    //@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response registerAccount(String info) {
        JSONObject json = new JSONObject(info);
        try {
            json = new AccessManage().register(new User(
                    json.getString("accountName")
                    , json.getString("password")
                    , json.getString("nickName")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert json != null;
        return Response.ok(
                new Gson().fromJson(
                json.toString()
                , new TypeToken<HashMap<String, String>>() {}.getType()))
                .build();
    }

    @POST
    @Path("/sign-in")
    @Consumes("application/json")
    @Produces("application/json")
    //@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response signIn (String info) {
        JSONObject jo = new JSONObject(info);
        User u = new User(jo.getString("username")
                , jo.getString("password")
                , "");
        try {
            jo = new AccessManage().signIn(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert jo != null;
        return Response.ok(
                new Gson().fromJson(
                jo.toString()
                , new TypeToken<HashMap<String, String>>() {}.getType()))
                .build();
    }

    @POST
    @Path("change-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(String info) {
        JSONObject jo = new JSONObject(info);
        try {
            jo = new AccessManage().changePassword(
                    new User(jo.getString("username")
                    , jo.getString("password")
                    , "")
                    , jo.getString("newPass")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Response.ok(
                new Gson().fromJson(
                    jo.toString()
                    , new TypeToken<HashMap<String, String>>() {}.getType())
        ).build();
    }

    @GET
    @Path("search-comic-by-name")
    @Produces("application/json")
    public Response searchComicByName(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        ArrayList<Comic> comics;
        try {
            comics = new AccessManage().searchComicByName(queryParams.getFirst("name"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Response.ok(comics).build();
    }

    @GET
    @Path("search-comic-by-cate")
    @Produces("application/json")
    public Response searchComicByCategory(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        ArrayList<Comic> comics;
        try {
            comics = new AccessManage().searchComicByCategory(
                    Integer.parseInt(queryParams.getFirst("cateId"))
                    , Integer.parseInt(queryParams.getFirst("orderBy")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Response.ok(comics).build();
    }

    @POST
    @Path("follow-book")
    @Consumes("application/json")
    @Produces("application/json")
    public Response followBook(String info) {
        JSONObject jo = new JSONObject(info);
        JSONObject reJO;
        try {
            reJO = new AccessManage().followBook(
                    jo.getString("userId")
                    , jo.getString("bookId"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Response.ok(
                new Gson().fromJson(reJO.toString()
                , new TypeToken<HashMap<String, String>>() {}.getType()))
                .build();
    }

    @POST
    @Path("unfollow-book")
    @Consumes("application/json")
    @Produces("application/json")
    public Response unfollowBook(String info) {
        JSONObject jo = new JSONObject(info);
        JSONObject reJO;
        try {
            reJO = new AccessManage().unfollowBook(
                    jo.getString("userId")
                    , jo.getString("bookId"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Response.ok(
                new Gson().fromJson(reJO.toString()
                , new TypeToken<HashMap<String, String>>() {}.getType()))
                .build();
    }

    @GET
    @Path("user-read-history")
    @Produces("application/json")
    public Response getUserReadHistory(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        ArrayList<Comic> comics;
        try {
            comics = new AccessManage().getUserReadHistory(queryParams.getFirst("userId"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Response.ok(comics).build();
    }

    @GET
    @Path("comic-list")
    @Produces("application/json")
    public Response getComicList(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        ArrayList<Comic> comics;
        try {
            comics = new AccessManage().getComicList(Integer.parseInt(queryParams.getFirst("size"))
                    , Integer.parseInt(queryParams.getFirst("page")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Response.ok(comics).build();
    }

    @GET
    @Path("/comic-info")
    @Produces("application/json")
    public Response getComicInfo (@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        Comic comic = null;
        try {
            comic = new AccessManage().getComicInfo(queryParams.getFirst("bookId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(comic).build();
    }

    @GET
    @Path("/comic-chapter-list")
    @Produces("application/json")
    public Response getComicChapterList (@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        ArrayList<ComicChapter> chapters = null;
        try {
            chapters = new AccessManage().getComicChapterList(queryParams.getFirst("bookId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(chapters).build();
    }

    @GET
    @Path("/comic-chapter-guest")
    @Produces("application/json")
    public Response getComicChapterGuest (@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        ArrayList<ComicPage> pages = new ArrayList<>();
        try {
            pages = new AccessManage().getComicChapterGuest(queryParams.getFirst("chapterId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(pages).build();
    }

    @GET
    @Path("/comic-chapter-user")
    @Produces("application/json")
    public Response getComicChapterUser(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        ArrayList<ComicPage> pages = new ArrayList<>();
        try {
            pages = new AccessManage().getComicChapterUser(
                    queryParams.getFirst("chapterId")
                    , queryParams.getFirst("userId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(pages).build();
    }

    @POST
    @Path("cms-create-new-comic")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response CMS_createNewComic(String info) {
        JSONObject jo = new JSONObject(info);
        Comic comic = new Comic();
        comic.setName(jo.getString("name"));
        comic.setDescription(jo.getString("description"));
        comic.setTags(jo.getString("tags"));
        comic.setCategory(jo.getString("category"));
        comic.setImage(jo.getString("image"));
        try {
            jo = new AccessManage().CMS_createNewComic(comic);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Response.ok(
                new Gson().fromJson(
                        jo.toString()
                        , new TypeToken<HashMap<String, String>>() {}.getType())
        ).build();
    }

    @POST
    @Path("cms-create-new-chapter")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response CMS_createNewChapter(String info) {
        JSONObject jo = new JSONObject(info);
        ComicChapter chapter = new ComicChapter();
        chapter.setName(jo.getString("name"));
        chapter.setBookId(jo.getString("bookId"));
        chapter.setPages(new Gson().fromJson(
                jo.getJSONArray("pages").toString()
                , new TypeToken<ArrayList<ComicChapter>>() {}.getType()));
        chapter.setSortOrder(jo.getInt("sortOrder"));
        try {
            jo = new AccessManage().CMS_createNewChapter(chapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Response.ok(
                new Gson().fromJson(
                    jo.toString()
                    , new TypeToken<HashMap<String, String>>(){}.getType()
                )
            )
            .build();
    }

    @DELETE
    @Path("cms-delete-book")
    @Produces("application/json")
    public Response CMS_deleteBook(String info) {
        JSONObject jo = new JSONObject(info);
        try {
            jo = new AccessManage().CSM_deleteBook(jo.getString("bookId"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Response.ok(
                new Gson().fromJson(
                        jo.toString()
                        , new TypeToken<HashMap<String, String>>() {}.getType()
                    )
                )
                .build();
    }

    @GET
    @Path("cms-get-user-list")
    @Produces("application/json")
    public Response CMS_getUserList(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        ArrayList<User> users;
        try {
            users = new AccessManage().CMS_getUserList(Integer.parseInt(queryParams.getFirst("size"))
                    , Integer.parseInt(queryParams.getFirst("page")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Response.ok(users).build();
    }

    @GET
    @Path("cms-search-user")
    @Produces("application/json")
    public Response CMS_searchUser(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        ArrayList<User> users;
        try {
            users = new AccessManage().CMS_searchUser(queryParams.getFirst("userId"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Response.ok(users).build();
    }
}
