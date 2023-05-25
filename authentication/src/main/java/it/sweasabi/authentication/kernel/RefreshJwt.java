package it.sweasabi.authentication;

class RefreshJwt extends BaseJwt
{
    public RefreshJwt(String Signature)
    {
        super(7200, Signature, "refresh");
    }
}