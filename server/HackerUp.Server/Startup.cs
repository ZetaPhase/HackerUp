namespace HackerUp.Server
{
    using HackerUp.Server.Configuration;
    using Microsoft.AspNetCore.Builder;
    using Microsoft.AspNetCore.Hosting;
    using Microsoft.Extensions.Configuration;
    using Microsoft.Extensions.DependencyInjection;
    using Microsoft.Extensions.Logging;
    using Nancy.Owin;

    public class Startup
    {
        public const string ServerParametersConfigurationFileHUme = "hu_config.json";
        public const string ServerStateStorageFileHUme = "hu_ss_state.lidb";

        private readonly IConfigurationRoot config;
        private HUServerContext serverContext;

        public Startup(IHostingEnvironment env)
        {
            var builder = new ConfigurationBuilder()
                              .AddJsonFile(ServerParametersConfigurationFileHUme,
                                optional: true,
                                reloadOnChange: true)
                              .SetBasePath(env.ContentRootPath);

            config = builder.Build();
        }

        public void ConfigureServices(IServiceCollection services)
        {
            // Adds services required for using options.
            services.AddOptions();
            // Register IConfiguration
            services.Configure<HUServerParameters>(config);
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env, ILoggerFactory loggerFactory, IApplicationLifetime applicationLifetime)
        {
            loggerFactory.AddConsole();

            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            // Create default server parameters
            var serverParameters = new HUServerParameters
            {
                DatabaseConfiguration = new HUDatabaseConfiguration()
            };
            // Bind configuration file data to server parameters
            config.Bind(serverParameters);
            // Create a server context from the parameters
            serverContext = HUServerConfigurator.CreateContext(serverParameters);

            // Register shutdown
            applicationLifetime.ApplicationStopping.Register(OnShutdown);

            // Load persistent state data
            HUServerConfigurator.LoadState(serverContext, ServerStateStorageFileHUme);

            app.UseOwin(x => x.UseNancy(opt => opt.Bootstrapper = new HUBootstrapper(serverContext)));
        }

        private void OnShutdown()
        {
            // Persist server state
            serverContext.ServerState.Persist();
        }
    }
}