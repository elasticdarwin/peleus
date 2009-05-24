#!/usr/bin/env ruby

require 'fileutils'
require 'rubygems'
require 'facets/shellwords'


file_name = File.expand_path(File.join(File.dirname(__FILE__), 'ShareSessionStateMachine.rl'))

file_extention = '.rl'
base_file_name = File.basename(file_name).chomp(file_extention)

target_path = File.join(File.dirname(__FILE__), '..', 'app', 'utils', 'statemachine', 'ShareSessionStateMachine.java')

temp_dir = File.join(File.dirname(__FILE__), '..', 'tmp', 'generated')

FileUtils.mkdir(temp_dir) unless File.directory?(temp_dir)


system("ragel -J #{file_name} -o #{target_path}")

dot_path = File.join(temp_dir, base_file_name +'.dot')
system("ragel -x #{file_name} | rlgen-dot > #{dot_path}")

png_path = File.join(temp_dir, base_file_name +'.png')
system("dot -Tpng #{dot_path} > #{png_path}")

system("gimv #{png_path} >/dev/null 2>&1 &")
