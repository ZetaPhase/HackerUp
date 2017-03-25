
using System;
using System.Threading.Tasks;
using HackerUp.Server.Configuration;

namespace HackerUp.Server.Events
{
    public class HUEventLoop
    {
        public IHUServerContext ServerContext { get; }
        public int RunCount { get; set; } = 0;

        public HUEventLoop(IHUServerContext serverContext)
        {
            ServerContext = serverContext;
            var t = RunAsync();
        }

        public async Task RunAsync()
        {
            while (true)
            {
                ++RunCount;
                await Task.Delay(1000);
                
                // Run instance
                await RunInstance();
            }
        }

        private async Task RunInstance()
        {
            // remove inactive users
            ServerContext.ConnectedUsers.RemoveAll(x => DateTime.Now.Subtract(x.LastPingTime) < TimeSpan.FromSeconds(30));
        }
    }
}