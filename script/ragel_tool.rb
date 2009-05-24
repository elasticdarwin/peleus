#!/usr/bin/env ruby

require 'fileutils'
require 'rubygems'
require 'facets/shellwords'


file_name = 'ShareSessionStateMachine.rl'

file_extention = File.extname(file_name)
base_file_name = file_name.chomp(file_extention)

target_path = File.join(File.dirname(__FILE__), '..', 'app', 'utils', 'ShareSessionStateMachine.java')

temp_dir = File.join(File.dirname(__FILE__), '..', 'tmp', 'generated')

FileUtils.mkdir(temp_dir) unless File.directory?(temp_dir)


system("ragel -J #{base_file_name}.rl -o #{target_path}")

dot_path = File.join(temp_dir, base_file_name +'.dot')
system("ragel -x #{base_file_name}.rl | rlgen-dot > #{dot_path}")

png_path = File.join(temp_dir, base_file_name +'.png')
system("dot -Tpng #{dot_path} > #{png_path}")

system("gimv #{png_path} >/dev/null 2>&1 &")
