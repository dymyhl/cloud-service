#!/bin/bash
echo '生成sql语句的表名:'${1}
echo '读取文件名称:'${2}
echo '开始生成时间文件----------'
awk -F'\\] \\[' 'print{ "#"$3}}' ./${2} > ./time.txt
echo '生成时间文件成功----------'
echo '开始生成用户id文件----------'
awk -F'userId\\":\\"' '{print $2}' ./${2} > ./user.txt
awk -F'\\"' '{print "&"$1"&"}' ./user.txt > ./userId.txt
echo '生成用户id文件成功----------'
echo '开始整合文件------------'
paste -d ./time.txt ./userId.txt ./${2} > ./dym.txt
echo '整合文件成功----------'
echo '开始拼接sql-----------'
sed 's/&//g' dym.txt
sed 's/&/','/'
sed 's/$/\'\);/'