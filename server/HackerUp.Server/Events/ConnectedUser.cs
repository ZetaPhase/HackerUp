
using System;
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

        public void Ping(DateTime time)
        {
            LastPingTime = time;
        }
    }
}