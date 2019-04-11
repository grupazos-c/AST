import java.util.ArrayList;

public class Post {

    private ArrayList<String> tags;
    private String post;

    public Post(String post,ArrayList<String> tags) {
        this.post = post;
        this.tags = tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    /**
     * @param post the post to set
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     * @return the tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    /**
     * @return the post
     */
    public String getPost() {
        return post;
    }

}