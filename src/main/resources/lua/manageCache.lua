-- manageCache.lua
local language = ARGV[1]
local location_id = ARGV[2]
local file_location = ARGV[3]

local key = "newspapers:" .. language .. ":" .. location_id

redis.call("SADD", key, file_location)
return true
