#!/usr/bin/env ruby
require 'erb'

config = File.read(File.join(File.dirname(__FILE__), '..', '/conf/application.conf.erb'))

if ARGV.size != 2
    puts 'run this script with:'
    puts 'ruby modify_db_setting.rb <db.user> <db.pass>'
end

db_user = ARGV[0]
db_pass = ARGV[1]


File.open(File.join(File.dirname(__FILE__), '..', '/conf/application.conf'), 'w') do |file|
    file.write(ERB.new(config).result(binding))
end

