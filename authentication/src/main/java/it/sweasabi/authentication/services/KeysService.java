package it.sweasabi.authentication.services;

public interface KeysService
{
    public String getRefreshKey();
    public String getAccessKey();
}