#!/bin/sh
#
PTYPE='Content-Type:application/json'

echo "Test starting..."
date

curl -s 'http://localhost:8888/version'; echo ""
curl -s -XPOST 'http://localhost:8888/version'; echo ""

##### GETs #####
curl -s 'http://localhost:8888/kblu/byText?text=aorta+tunica+media' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=aorta+tunica+media' | wc
curl -s 'http://localhost:8888/kblu/byText?text=BLOOD' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=BLOOD' | wc
curl -s 'http://localhost:8888/kblu/byText?text=Blood' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=Blood' | wc
curl -s 'http://localhost:8888/kblu/byText?text=blood' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=blood' | wc
curl -s 'http://localhost:8888/kblu/byText?text=brown+fat' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=brown+fat' | wc
curl -s 'http://localhost:8888/kblu/byText?text=cell' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=cell' | wc
curl -s 'http://localhost:8888/kblu/byText?text=Pancreas' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=Pancreas' | wc
curl -s 'http://localhost:8888/kblu/byText?text=heart' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=heart' | wc
curl -s 'http://localhost:8888/kblu/byText?text=Zygomatic+muscle' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=Zygomatic+muscle' | wc
curl -s 'http://localhost:8888/kblu/byText?text=zygoma' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=zygoma' | wc

curl -s 'http://localhost:8888/kblu/byText?text=AKT1' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=AKT1' | wc
curl -s 'http://localhost:8888/kblu/byText?text=Akt1' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=Akt1' | wc
curl -s 'http://localhost:8888/kblu/byText?text=akt1' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=akt1' | wc
curl -s 'http://localhost:8888/kblu/byText?text=AKT2' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=AKT2' | wc
curl -s 'http://localhost:8888/kblu/byText?text=Akt2' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=Akt2' | wc
curl -s 'http://localhost:8888/kblu/byText?text=akt2' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=akt2' | wc
curl -s 'http://localhost:8888/kblu/byText?text=FOXP3' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=FOXP3' | wc
curl -s 'http://localhost:8888/kblu/byText?text=foxP3' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=foxP3' | wc
curl -s 'http://localhost:8888/kblu/byText?text=MEK5' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=MEK5' | wc
curl -s 'http://localhost:8888/kblu/byText?text=MEK+5' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=MEK+5' | wc

curl -s 'http://localhost:8888/kblu/byText?text=apoptosis' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=apoptosis' | wc
curl -s 'http://localhost:8888/kblu/byText?text=apoptotic+process' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=apoptotic+process' | wc
curl -s 'http://localhost:8888/kblu/byText?text=cell+adhesion' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=cell+adhesion' | wc
curl -s 'http://localhost:8888/kblu/byText?text=protein+degradation' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=protein+degradation' | wc
curl -s 'http://localhost:8888/kblu/byText?text=senescence' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=senescence' | wc
curl -s 'http://localhost:8888/kblu/byText?text=programmed+cell+death' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=programmed+cell+death' | wc
curl -s 'http://localhost:8888/kblu/byText?text=hematopoiesis' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=hematopoiesis' | wc
curl -s 'http://localhost:8888/kblu/byText?text=glycolysis' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=glycolysis' | wc
curl -s 'http://localhost:8888/kblu/byText?text=cell-cell+adhesion' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=cell-cell+adhesion' | wc
curl -s 'http://localhost:8888/kblu/byText?text=tricarboxylic+acid+cycle' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=tricarboxylic+acid+cycle' | wc

curl -s 'http://localhost:8888/kblu/byText?text=GTP' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=GTP' | wc
curl -s 'http://localhost:8888/kblu/byText?text=estradiol' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=estradiol' | wc
curl -s 'http://localhost:8888/kblu/byText?text=hexabromocyclododecane' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=hexabromocyclododecane' | wc
curl -s 'http://localhost:8888/kblu/byText?text=Ethyl+Methanesulfonate' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=Ethyl+Methanesulfonate' | wc
curl -s 'http://localhost:8888/kblu/byText?text=ROSox' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=ROSox' | wc
curl -s 'http://localhost:8888/kblu/byText?text=ROSsox' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=ROSsox' | wc
curl -s 'http://localhost:8888/kblu/byText?text=Fenosmoline' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=Fenosmoline' | wc
curl -s 'http://localhost:8888/kblu/byText?text=Ferric+orthophosphate' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=Ferric+orthophosphate' | wc
curl -s 'http://localhost:8888/kblu/byText?text=GDP-D-Mannose' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=GDP-D-Mannose' | wc
curl -s 'http://localhost:8888/kblu/byText?text=vitamin+B1' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=vitamin+B1' | wc


##### POSTs #####
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "aorta tunica media"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "aorta tunica media"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "BLOOD"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "BLOOD"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "Blood"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "Blood"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "blood"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "blood"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "brown fat"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "brown fat"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "cell"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "cell"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "Pancreas"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "Pancreas"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "heart"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "heart"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "Zygomatic muscle"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "Zygomatic muscle"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "zygoma"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "zygoma"}' -H "$PTYPE" | wc

curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "AKT1"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "AKT1"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "Akt1"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "Akt1"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "akt1"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "akt1"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "AKT2"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "AKT2"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "Akt2"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "Akt2"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "akt2"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "akt2"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "FOXP3"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "FOXP3"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "foxP3"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "foxP3"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "MEK5"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "MEK5"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "MEK 5"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "MEK 5"}' -H "$PTYPE" | wc

curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "apoptosis"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "apoptosis"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "apoptotic process"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "apoptotic process"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "cell adhesion"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "cell adhesion"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "protein degradation"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "protein degradation"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "senescence"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "senescence"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "programmed cell death"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "programmed cell death"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "hematopoiesis"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "hematopoiesis"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "glycolysis"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "glycolysis"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "cell-cell adhesion"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "cell-cell adhesion"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "tricarboxylic acid cycle"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "tricarboxylic acid cycle"}' -H "$PTYPE" | wc

curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "GTP"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "GTP"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "estradiol"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "estradiol"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "hexabromocyclododecane"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "hexabromocyclododecane"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "Ethyl Methanesulfonate"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "Ethyl Methanesulfonate"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "ROSox"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "ROSox"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "ROSsox"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "ROSsox"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "Fenosmoline"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "Fenosmoline"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "Ferric orthophosphate"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "Ferric orthophosphate"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "GDP-D-Mannose"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "GDP-D-Mannose"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText' -d'{"text": "vitamin B1"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup' -d'{"text": "vitamin B1"}' -H "$PTYPE" | wc

echo "Test ending..."
date
