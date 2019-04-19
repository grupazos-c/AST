import java.util.ArrayList;

public class Post {

    private ArrayList<String> tags;
    private String post;
    private String autor;

    public Post(String post,ArrayList<String> tags, String autor) {
        this.post = post;
        this.tags = tags;
        this.autor = autor;
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

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

}