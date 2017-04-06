#!/bin/sh

echo "Test starting..."
date

curl -s 'http://localhost:8888/version'
curl -s -XPOST 'http://localhost:8888/version'

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
curl -s 'http://localhost:8888/kblu/byText?text=MEK15' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=MEK15' | wc
curl -s 'http://localhost:8888/kblu/byText?text=MEK+15' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=MEK+15' | wc

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
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=aorta+tunica+media' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=aorta+tunica+media' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=BLOOD' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=BLOOD' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=Blood' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=Blood' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=blood' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=blood' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=brown+fat' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=brown+fat' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=cell' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=cell' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=Pancreas' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=Pancreas' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=heart' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=heart' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=Zygomatic+muscle' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=Zygomatic+muscle' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=zygoma' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=zygoma' | wc

curl -s -XPOST 'http://localhost:8888/kblu/byText?text=AKT1' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=AKT1' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=Akt1' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=Akt1' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=akt1' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=akt1' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=AKT2' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=AKT2' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=Akt2' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=Akt2' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=akt2' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=akt2' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=FOXP3' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=FOXP3' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=foxP3' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=foxP3' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=MEK15' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=MEK15' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=MEK+15' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=MEK+15' | wc

curl -s -XPOST 'http://localhost:8888/kblu/byText?text=apoptosis' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=apoptosis' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=apoptotic+process' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=apoptotic+process' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=cell+adhesion' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=cell+adhesion' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=protein+degradation' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=protein+degradation' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=senescence' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=senescence' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=programmed+cell+death' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=programmed+cell+death' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=hematopoiesis' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=hematopoiesis' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=glycolysis' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=glycolysis' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=cell-cell+adhesion' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=cell-cell+adhesion' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=tricarboxylic+acid+cycle' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=tricarboxylic+acid+cycle' | wc

curl -s -XPOST 'http://localhost:8888/kblu/byText?text=GTP' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=GTP' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=estradiol' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=estradiol' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=hexabromocyclododecane' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=hexabromocyclododecane' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=Ethyl+Methanesulfonate' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=Ethyl+Methanesulfonate' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=ROSox' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=ROSox' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=ROSsox' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=ROSsox' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=Fenosmoline' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=Fenosmoline' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=Ferric+orthophosphate' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=Ferric+orthophosphate' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=GDP-D-Mannose' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=GDP-D-Mannose' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText?text=vitamin+B1' | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup?text=vitamin+B1' | wc

echo "Test ending..."
date
