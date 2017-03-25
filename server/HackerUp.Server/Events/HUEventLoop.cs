
using System;
using System.Threading.Tasks;
using HackerUp.Server.Configuration;

namespace HackerUp.Server.Events
{
    public class HUEventLoop
    {
        public IHUServerContext ServerContext { get; }

        public HUEventLoop(IHUServerContext serverContext)
        {
            ServerContext = serverContext;
            var t = RunAsync();
        }

        public async Task RunAsync()
        {
            while (true)
            {
                await Task.Delay(1000);
                
                // Run instance
                await RunInstance();
            }
        }

        private async Task RunInstance()
        {
            
        }
    }
}