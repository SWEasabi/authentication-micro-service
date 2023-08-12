package it.SWEasabi.authentication.kernel;

public record LoginResult(boolean status, String access, String refresh)
{
}
