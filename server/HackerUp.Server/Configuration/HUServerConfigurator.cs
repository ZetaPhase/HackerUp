using LiteDB;
using System.IO;
using System.Linq;

namespace HackerUp.Server.Configuration
{
    public static class HUServerConfigurator
    {
        internal static HUServerContext CreateContext(HUServerParameters serverParameters)
        {
            // load the parameters
            serverParameters.BaseDirectory = Directory.GetCurrentDirectory();
            var context = new HUServerContext(serverParameters);
            return context;
        }

        public const string StateStorageKey = "state";

        public static void LoadState(HUServerContext serverContext, string stateStorageFile)
        {
            // Load the Server State into the context. This object also includes the OsmiumMine Core state
            var database = new LiteDatabase(stateStorageFile);
            var stateStorage = database.GetCollection<HUServerState>(StateStorageKey);
            var savedState = stateStorage.FindAll().FirstOrDefault();
            if (savedState == null)
            {
                // Create and save new state
                savedState = new HUServerState();
                stateStorage.Upsert(savedState);
            }
            // Update context
            savedState.PersistenceMedium = stateStorage;
            savedState.Persist = () =>
            {
                // Update in database
                stateStorage.Upsert(savedState);
            };
            // Check for the KeyReset flag
            if (serverContext.Parameters.KeyReset)
            {
                savedState.ApiKeys.Clear();
            }
            // Merge the API keys
            foreach (var paramKey in serverContext.Parameters.ApiKeys)
            {
                // look for a matching key
                var existingKey = savedState.ApiKeys.FirstOrDefault(x => x.Key == paramKey.Key);
                if (existingKey == null)
                {
                    savedState.ApiKeys.Add(paramKey);
                }
                else
                {
                    // Remove the old key and add the new key
                    savedState.ApiKeys.Remove(existingKey);
                    savedState.ApiKeys.Add(paramKey);
                }
            }
            // Save the state
            //savedState.PersistenceMedium.Upsert(savedState);
            savedState.Persist();
            // Update references
            serverContext.ServerState = savedState;
        }
    }
}