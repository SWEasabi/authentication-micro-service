package it.sweasabi.authentication.services;

public class LocalKeysService implements KeysService
{
    public String getRefreshKey()
    {
        return "chiavePrivata";
    }
    public String getAccessKey()
    {
        return "chiavePubblica";
    }
}