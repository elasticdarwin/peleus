#!/usr/bin/env ruby

require 'yaml'
require 'rubygems'
require 'active_record'

ActiveRecord::Base.establish_connection(:adapter => 'mysql', :database => 'peleus_development', :username => 'root', :password => '123456')

def generate_fixture(class_name,key)
    result = Kernel.const_get(class_name).find(:all).map(&:attributes).inject({}) do |result, attributes |

       result["#{class_name}(#{attributes[key]})"] = attributes.reject {|attribute_key, attribute_value| attribute_key == 'id'}
       result
    end

    puts YAML.dump(result)
end 

MODELS = {:User => 'name',
          ShareSession: 'id',
          Department: 'name'}

MODELS.each do |class_name, key|

    self.class.module_eval %{
        class #{class_name} < ActiveRecord::Base
        end
    }

    generate_fixture(class_name, key)
end


