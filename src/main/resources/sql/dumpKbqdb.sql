#!/bin/sh
ftime=`date +%y-%m-%d_%H.%M`
fname="kbqdb-twelf_${ftime}.sql"
fyl=${1:-${fname}}
mysqldump -u root -p --allow-keywords --complete-insert --disable-keys --extended-insert --no-autocommit --single-transaction --quick kbqdb >$fyl
chmod 440 $fyl
