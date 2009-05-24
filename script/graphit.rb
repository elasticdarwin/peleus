require 'rubygems'
require 'enumerator'
require 'git'

total = 365 # count from init project
g = Git.open(working_dir = File.join(File.dirname(__FILE__), '..'))

file_set = {}

g.log.enum_for(:each).to_a.reverse.inject do |last, cur|
    diff = g.diff(last, cur)
    total_stats = diff.stats[:total]
    total = total + total_stats[:insertions] - total_stats[:deletions]
    diff_files = diff.stats[:files].sort { |a, b| a[0] <=> b[0] }
    
    diff_files.each do |file_name, stats|
        printf("%-60s %6d %6d\n", file_name, stats[:insertions], stats[:deletions])
    end

    puts "From revision <#{diff.from}> to <#{diff.to}>"
    puts "Total line number is <#{total}>, with <#{total_stats[:insertions]}> insertions and <#{total_stats[:deletions]}> deletions in this interation."

    3.times { puts }
    cur
end

