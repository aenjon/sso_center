#!/bin/bash -vx

backuppath=/root/zhuzi/mysql/backup
date=`date +%Y-%m-%d`
dbsource=test
host=192.168.18.198

#backup database
mysqldump -h${host} -uroot -p000000 --opt --default-character-set=utf8 --hex-blob  -R ${dbsource} | sed -e 's/DEFINER[ ]*=[ ]*[^*]*\*/\*/' |gzip>${backuppath}/testfulldb-${date}.gz

#only structure no trigger
mysqldump  -h${host} -uroot -p000000 -R --skip-comments --skip-triggers --opt --default-character-set=utf8  --no-data --add-drop-database ${dbsource} | sed -e 's/DEFINER[ ]*=[ ]*[^*]*\*/\*/' | sed 's/ AUTO_INCREMENT=[0-9]*\b//' > ${backuppath}/struct-${date}.sql

mysqldump  -h${host} -uroot -p000000 -R --skip-comments --skip-triggers --opt --default-character-set=utf8  --no-data --no-create-info ${dbsource} | sed -e 's/DEFINER[ ]*=[ ]*[^*]*\*/\*/' | sed 's/ AUTO_INCREMENT=[0-9]*\b//' > ${backuppath}/struct_nocreateinfo-${date}.sql;

mysqldump  -h${host} -uroot -p000000 --default-character-set=utf8 --skip-comments --events --no-data --routines --no-create-info --no-create-db --opt ${dbsource} | sed -e 's/DEFINER[ ]*=[ ]*[^*]*\*/\*/' >${backuppath}/trigger-${date}.sql;

mysqldump  -h${host} -uroot -p000000 --default-character-set=utf8 --skip-comments --add-drop-table --hex-blob ${dbsource} tb3rdclients tbsystemproperties tb3rdfilter tbschoolinvite  | sed -e 's/DEFINER[ ]*=[ ]*[^*]*\*/\*/' | sed 's/ AUTO_INCREMENT=[0-9]*\b//' >${backuppath}/data.sql


#mysqldump  -h${host} -uroot -p000000 --default-character-set=utf8 --skip-comments --add-drop-table --hex-blob ${dbsource} book orders user | sed -e 's/DEFINER[ ]*=[ ]*[^*]*\*/\*/' | sed 's/ AUTO_INCREMENT=[0-9]*\b//' >${backuppath}/data-${date}.sql
