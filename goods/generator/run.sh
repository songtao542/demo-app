#!/usr/bin/env bash

rm -rf ../src/main/java/com/aperise/bean/*

rm -rf ../src/main/java/com/aperise/mapper/gen/*

rm -rf ../src/main/resources/mapper/gen/*

java -jar mybatis-generator-core-1.3.5.jar -configfile config.xml -overwrite

#cd ../src/main/resources/mapper/gen/ && ls | xargs sed -i 's/com.aperise.mapper.gen/com.aperise.mapper/g' &&  ls | xargs sed -i 's/Example_/Criteria_/g' && ls | xargs -I {} mv {} Gen{}
cd ../src/main/resources/mapper/gen/ && ls | xargs sed -i '' 's/com.aperise.mapper.gen/com.aperise.mapper/g' &&  ls | xargs sed -i '' 's/ByExample/ByCriteria/g' && ls | xargs -I {} mv {} Gen{}

cd ../../../java/com/aperise/mapper/gen/ && ls | xargs -I {} sed -i '' 's/public interface /public interface Gen/g' {} &&  ls | xargs sed -i '' 's/ByExample/ByCriteria/g' && ls | xargs -I {} mv {} Gen{}
