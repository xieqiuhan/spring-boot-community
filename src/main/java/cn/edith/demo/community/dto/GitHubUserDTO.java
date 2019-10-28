package cn.edith.demo.community.dto;

/**
 * 
 */
public class GitHubUserDTO {
    private String name;
    private Long id;
    private String bio;

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Override
	public String toString() {
		return "GitHubUserDTO [bio=" + bio + ", id=" + id + ", name=" + name + "]";
	}


    }

