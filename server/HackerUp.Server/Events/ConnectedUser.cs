
using System;
using GeoCoordinatePortable;
using HackerUp.Server.DataModels;

namespace HackerUp.Server.Events
{
    public class ConnectedUser
    {
        public ConnectedUser(RegisteredUser dbUser)
        {
            DbUser = dbUser;
            LastPingTime = DateTime.Now;
        }
        public RegisteredUser DbUser { get; }

        public DateTime LastPingTime { get; private set; }

        public void Ping(GeoCoordinate coord)
        {
            LastPingTime = DateTime.Now;
        }
    }
}