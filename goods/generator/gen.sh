#!/usr/bin/env bash

gen=$1
gen_dir=$(pwd)
bean_dir="${gen_dir}/../src/main/java/com/aperise/bean"
mapper_dir="${gen_dir}/../src/main/java/com/aperise/mapper/gen"
xml_dir="${gen_dir}/../src/main/resources/mapper/gen"
tmp_dir="${gen_dir}/../build/tmp"

if [ ${gen} = "bean" ]; then
cd ${bean_dir}
c=$(ls | wc -l)
if [ $c -gt 0 ]; then
#去掉.java后缀
ls | xargs rename 's/.java//'
#创建tmp目录，并将去掉.java后缀的文件移动到tmp目录中
mkdir "${bean_dir}/tmp/"
#ls | xargs -I {} mv {} ./tmp/
find ./ -maxdepth 1 -type f -exec mv {} ./tmp/ \;
fi
fi

##同样的方式对mapper目录操作
#cd ${mapper_dir}
#c=$(ls | wc -l)
#if [ $c -gt 0 ]; then
##去掉.java后缀
#ls | xargs rename 's/.java//'
##创建tmp目录，并将去掉.java后缀的文件移动到tmp目录中
#mkdir "${mapper_dir}/tmp/"
##ls | xargs -I {} mv {} ./tmp/
#find ./ -maxdepth 1 -type f -exec mv {} ./tmp/ \;
#fi

#重新生成之前先删除xml文件
rm -rf ${xml_dir}/*
rm -rf ${mapper_dir}/*

if [ ! -d ${tmp_dir} ]; then
mkdir ${tmp_dir}
fi

#进入generator所在目录执行
cd ${gen_dir}
if [ ${gen} = "bean" ]; then
java -jar mybatis-generator-core-1.3.5.jar -configfile config_bean.xml -overwrite
elif [ ${gen} = "mapper" ]; then
java -jar mybatis-generator-core-1.3.5.jar -configfile config_mapper.xml -overwrite
else
java -jar mybatis-generator-core-1.3.5.jar -configfile config.xml -overwrite
fi

if [ $(uname) == "Darwin" ]; then

if [ ${gen} = "bean" -o ${gen} = "" ]; then
#进入bean目录
cd ${bean_dir}
if [ -d "tmp" ]; then
#将新生成的文件重命名
ls | xargs rename 's/.java/.new/'
#进入tmp目录遍历文件
cd "${bean_dir}/tmp"
#合并新生成的文件和旧文件
#ls | xargs -I {} sh -c "diff -DVERSION1 {} ../{}.new > ../{}.java"
find ./ -maxdepth 1 -type f -exec sh -c "diff -DVERSION1 {} ../{}.new > ../{}.java" \;
cd ${bean_dir}
#删除临时文件
rm -rf tmp
rm -rf *.new
#遍历合并之后的Java文件去掉#号开头的行
#ls | xargs -I {} sed -i '/^#.*/d' {}
#ls | xargs -I {} sh -c "grep -v '^#.*' {} > {}"
#find ./ -maxdepth 1 -type f -exec sed -i '/^#.*/d' {} \;
fi
fi

#if [ ${gen} = "mapper" -o ${gen} = "" ]; then
#cd ${xml_dir} && ls | xargs sed -i 's/com.aperise.mapper.gen/com.aperise.mapper/g' &&  ls | xargs sed -i 's/ByExample/ByCriteria/g' && ls | xargs -I {} mv {} Gen{}
#cd ${mapper_dir} && ls | xargs -I {} sed -i 's/public interface /public interface Gen/g' {} &&  ls | xargs sed -i 's/ByExample/ByCriteria/g' && ls | xargs -I {} mv {} Gen{}
#fi

else

if [ ${gen} = "bean" -o ${gen} = "" ]; then
#进入bean目录
cd ${bean_dir}
if [ -d "tmp" ]; then
#将新生成的文件重命名
ls | xargs rename 's/.java/.new/'
#进入tmp目录遍历文件
cd "${bean_dir}/tmp"
#合并新生成的文件和旧文件
#ls | xargs -I {} sh -c "diff -DVERSION1 {} ../{}.new > ../{}.java"
find ./ -maxdepth 1 -type f -exec sh -c "diff -DVERSION1 {} ../{}.new > ../{}.java" \;
cd ${bean_dir}
#删除临时文件
rm -rf tmp
rm -rf *.new
#遍历合并之后的Java文件去掉#号开头的行
#ls | xargs -I {} sed -i '/^#.*/d' {}
#ls | xargs -I {} sh -c "grep -v '^#.*' {} > {}"
#find ./ -maxdepth 1 -type f -exec sed -i '/^#.*/d' {} \;
fi
fi

##同样的方式对mapper目录操作
##进入mapper目录
#cd ${mapper_dir}
#if [ -d "tmp" ]; then
##将新生成的文件重命名
#ls | xargs rename 's/.java/.new/'
##进入tmp目录遍历文件
#cd "${mapper_dir}/tmp"
##合并新生成的文件和旧文件
##ls | xargs -I {} sh -c "diff -DVERSION1 {} ../{}.new > ../{}.java"
#find ./ -maxdepth 1 -type f -exec sh -c "diff -DVERSION1 {} ../{}.new > ../{}.java" \;
#cd ${mapper_dir}
##删除临时文件
#rm -rf tmp
#rm -rf *.new
##遍历合并之后的Java文件去掉#号开头的行
##ls | xargs -I {} sed -i '/^#.*/d' {}
##ls | xargs -I {} sh -c "grep -v '^#.*' {} > {}"
##find ./ -maxdepth 1 -type f -exec sed -i '/^#.*/d' {} \;
#fi

if [ ${gen} = "mapper" -o ${gen} = "" ]; then
cd ${xml_dir} && ls | xargs sed -i 's/com.aperise.mapper.gen/com.aperise.mapper/g' &&  ls | xargs sed -i 's/ByExample/ByCriteria/g' && ls | xargs -I {} mv {} Gen{}
cd ${mapper_dir} && ls | xargs -I {} sed -i 's/public interface /public interface Gen/g' {} &&  ls | xargs sed -i 's/ByExample/ByCriteria/g' && ls | xargs -I {} mv {} Gen{}
fi

if [ ${gen} = "bean" -o ${gen} = "mapper" ]; then
rm -rf ${tmp_dir}
fi

fi

