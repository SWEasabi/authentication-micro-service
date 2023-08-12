package it.SWEasabi.authentication.kernel;

public class LoginResult
{
    private boolean status;
    private String accessJwt, refreshJwt;
    public LoginResult(boolean Status, String AccessJwt, String RefreshJwt)
    {
        status = Status;
        accessJwt = AccessJwt;
        refreshJwt = RefreshJwt;
    }
    public boolean getStatus()
    {
        return status;
    }
    public String getAccessJwt()
    {
        return accessJwt;
    }
    public String getRefreshJwt()
    {
        return refreshJwt;
    }
}
