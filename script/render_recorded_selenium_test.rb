#!/usr/bin/env ruby

content = STDIN.read

content.gsub!(/^(\s*\w+)\s+"/) {|result| "#{$1}('" }
content.gsub!(/"$/, "')")
content.gsub!(/"/, "'")
content.gsub!(/='([^']*)'/) {|result| %{="#{$1}"} }

3.times {puts}
puts '#######################################'
3.times {puts}
puts content



## demo inputs as follows
#
#   content =<<-EOF
#   open "/myspace/sharesession"
#   clickAndWait "//input[@value='close']"
#   clickAndWait "link=exact:Not have an account yet?"
#   type "user_form.name", "asf"
#   type "user_form.email", "asdf"
#   type "user_form.password", "asdf"
#   type "user_form.repeat_password", "asdfsadfasdf"
#   clickAndWait "//input[@value='Join Now']"
#   type "user_form.email", "asdf@sadfsa.asdfas"
#   type "user_form.password", "1234"
#   type "user_form.repeat_password", "12341234"
#   type "user_form.password", "12341234"
#   clickAndWait "//input[@value='Join Now']"
#   EOF


