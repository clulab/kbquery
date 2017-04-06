#!/bin/sh

echo "Test starting..."
date

curl -s 'http://localhost:8888/version'
curl -s -XPOST 'http://localhost:8888/version'

##### GETs #####
curl -s 'http://localhost:8888/kblu/byNsId?nsId=uniprot:P31749' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=P31749' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=uniprot:P31749' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=uniprot:Q99683' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=Q99683' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=uniprot:Q99683' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=uniprot:Q13131' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=Q13131' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=uniprot:Q13131' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=uniprot:Q02750' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=Q02750' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=uniprot:Q02750' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=uniprot:P36507' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=P36507' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=uniprot:P36507' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=uniprot:P01116' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=P01116' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=uniprot:P01116' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=uniprot:Q9P0J7' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=Q9P0J7' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=uniprot:Q9P0J7' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=uniprot:P15056' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=P15056' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=uniprot:P15056' | wc

curl -s 'http://localhost:8888/kblu/byNsId?nsId=pubchem:5870' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=pubchem&id=5870' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=pubchem:5870' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=pubchem:6830' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=pubchem&id=6830' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=pubchem:6830' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=pubchem:16666708' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=pubchem&id=16666708' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=pubchem:16666708' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=pubchem:441276' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=pubchem&id=441276' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=pubchem:441276' | wc

curl -s 'http://localhost:8888/kblu/byNsId?nsId=pfam:PF01756' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=pfam&id=PF01756' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=pfam:PF01756' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=pfam:PF00091' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=pfam&id=PF00091' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=pfam:PF00091' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=pfam:PF00110' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=pfam&id=PF00110' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=pfam:PF00110' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=pfam:PF04901' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=pfam&id=PF04901' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=pfam:PF04901' | wc

curl -s 'http://localhost:8888/kblu/byNsId?nsId=taxonomy:3702' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=taxonomy&id=3702' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=taxonomy:3702' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=taxonomy:10029' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=taxonomy&id=10029' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=taxonomy:10029' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=taxonomy:36278' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=taxonomy&id=36278' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=taxonomy:36278' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=taxonomy:10116' | wc
curl -s 'http://localhost:8888/kblu/byNsAndId?ns=taxonomy&id=10116' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=taxonomy:10116' | wc


##### POSTs #####
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=uniprot:P31749' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=P31749' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=uniprot:P31749' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=uniprot:Q99683' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=Q99683' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=uniprot:Q99683' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=uniprot:Q13131' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=Q13131' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=uniprot:Q13131' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=uniprot:Q02750' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=Q02750' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=uniprot:Q02750' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=uniprot:P36507' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=P36507' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=uniprot:P36507' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=uniprot:P01116' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=P01116' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=uniprot:P01116' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=uniprot:Q9P0J7' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=Q9P0J7' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=uniprot:Q9P0J7' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=uniprot:P15056' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=uniprot&id=P15056' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=uniprot:P15056' | wc

curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=pubchem:5870' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=pubchem&id=5870' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=pubchem:5870' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=pubchem:6830' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=pubchem&id=6830' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=pubchem:6830' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=pubchem:16666708' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=pubchem&id=16666708' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=pubchem:16666708' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=pubchem:441276' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=pubchem&id=441276' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=pubchem:441276' | wc

curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=pfam:PF01756' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=pfam&id=PF01756' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=pfam:PF01756' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=pfam:PF00091' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=pfam&id=PF00091' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=pfam:PF00091' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=pfam:PF00110' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=pfam&id=PF00110' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=pfam:PF00110' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=pfam:PF04901' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=pfam&id=PF04901' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=pfam:PF04901' | wc

curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=taxonomy:3702' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=taxonomy&id=3702' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=taxonomy:3702' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=taxonomy:10029' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=taxonomy&id=10029' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=taxonomy:10029' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=taxonomy:36278' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=taxonomy&id=36278' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=taxonomy:36278' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId?nsId=taxonomy:10116' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsAndId?ns=taxonomy&id=10116' | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms?nsId=taxonomy:10116' | wc

echo "Test ending..."
date
