package capsulestudio.loginandregi;

import com.google.gson.annotations.SerializedName;

public class GetUserName
{
    @SerializedName("id")
    private int id;
    @SerializedName("success")
    private int status;
    @SerializedName("message")
    private String message;

    public GetUserName(int id,int status, String message) {

        this.id = id;
        this.status = status;
        this.message = message;
    }

    public GetUserName() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}