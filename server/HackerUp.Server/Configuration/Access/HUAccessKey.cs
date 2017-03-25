
using OsmiumSubstrate.Configuration.Access;

namespace HackerUp.Server.Configuration.Access
{
    /// <summary>
    /// An enumeration of access scope identifiers, used for granular permission grants on key access
    /// </summary>
    public enum HUApiAccessScope
    {
        None = 1 << 0,
        
        Default = 1 << 1,

        Admin = 1 << 31,
    }

    public class HUAccessKey : AccessKey<HUApiAccessScope>
    {
        public override HUApiAccessScope[] AccessScopes { get; set; } = new[] { HUApiAccessScope.Default };
    }
}