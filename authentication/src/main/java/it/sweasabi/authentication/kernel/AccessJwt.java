package it.sweasabi.authentication;

class AccessJwt extends BaseJwt
{
    public AccessJwt(String Signature)
    {
        super(120, Signature, "access");
    }
}