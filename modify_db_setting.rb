require 'erb'

config = File.read('conf/application.conf.erb')

db_user = 'root'
db_pass = '123456'


File.open('conf/application.conf', 'w') do |file|
    file.write(ERB.new(config).result(binding))
end

