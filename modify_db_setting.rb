require 'erb'
#   require 'cmdparse'


#   cmd = CmdParse::CommandParser.new( true, true )
#   cmd.program_name = "net"
#   cmd.program_version = [0, 1, 1]

#   cmd.options = CmdParse::OptionParserWrapper.new do |opt|
#         opt.separator "Global options:"
#           opt.on("--verbose", "Be verbose when outputting info") {|t| $verbose = true }
#   end

#   cmd.add_command( CmdParse::HelpCommand.new )
#   cmd.add_command( CmdParse::VersionCommand.new )


#   cmd.parse

config = File.read('conf/application.conf.erb')

if ARGV.size != 2
    puts 'run this script with:'
    puts 'ruby modify_db_setting.rb <db.user> <db.pass>'
end

db_user = ARGV[0]
db_pass = ARGV[1]


File.open('conf/application.conf', 'w') do |file|
    file.write(ERB.new(config).result(binding))
end

