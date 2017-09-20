#!/usr/bin/env bash

gen_dir=$(pwd)
bean_dir="${gen_dir}/../src/main/java/com/aperise/bean"
mapper_dir="${gen_dir}/../src/main/java/com/aperise/mapper/gen"
xml_dir="${gen_dir}/../src/main/resources/mapper/gen"

rm -rf ${bean_dir}/*
rm -rf ${mapper_dir}/*
rm -rf ${xml_dir}/*

java -jar mybatis-generator-core-1.3.5.jar -configfile config.xml -overwrite

if [ $(uname) == "Darwin" ]; then

#cd ../src/main/resources/mapper/gen/ && ls | xargs sed -i 's/com.aperise.mapper.gen/com.aperise.mapper/g' &&  ls | xargs sed -i 's/Example_/Criteria_/g' && ls | xargs -I {} mv {} Gen{}
cd ${xml_dir} && ls | xargs sed -i 's/com.aperise.mapper.gen/com.aperise.mapper/g' &&  ls | xargs sed -i 's/ByExample/ByCriteria/g' && ls | xargs -I {} mv {} Gen{}
cd ${mapper_dir} && ls | xargs -I {} sed -i 's/public interface /public interface Gen/g' {} &&  ls | xargs sed -i 's/ByExample/ByCriteria/g' && ls | xargs -I {} mv {} Gen{}

else

cd ${xml_dir} && ls | xargs sed -i 's/com.aperise.mapper.gen/com.aperise.mapper/g' &&  ls | xargs sed -i 's/ByExample/ByCriteria/g' && ls | xargs -I {} mv {} Gen{}
cd ${mapper_dir} && ls | xargs -I {} sed -i 's/public interface /public interface Gen/g' {} &&  ls | xargs sed -i 's/ByExample/ByCriteria/g' && ls | xargs -I {} mv {} Gen{}

fi

