#!/bin/sh
#
PTYPE='Content-Type:application/json'
PORT=${1:-8888}

echo "Test starting..."
date

curl -s "http://localhost:${PORT}/version"; echo ""

##### GETs #####
curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=uniprot:P31749" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=uniprot&id=P31749" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=uniprot:P31749" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=P31749" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=uniprot:Q99683" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=uniprot&id=Q99683" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=uniprot:Q99683" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=Q99683" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=uniprot:Q13131" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=uniprot&id=Q13131" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=uniprot:Q13131" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=Q13131" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=uniprot:Q02750" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=uniprot&id=Q02750" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=uniprot:Q02750" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=Q02750" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=uniprot:P36507" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=uniprot&id=P36507" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=uniprot:P36507" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=P36507" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=uniprot:P01116" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=uniprot&id=P01116" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=uniprot:P01116" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=P01116" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=uniprot:Q9P0J7" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=uniprot&id=Q9P0J7" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=uniprot:Q9P0J7" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=Q9P0J7" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=uniprot:P15056" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=uniprot&id=P15056" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=uniprot:P15056" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=P15056" | wc


curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=pubchem:5870" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=pubchem&id=5870" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=pubchem:5870" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=5870" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=pubchem:6830" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=pubchem&id=6830" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=pubchem:6830" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=6830" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=pubchem:16666708" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=pubchem&id=16666708" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=pubchem:16666708" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=16666708" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=pubchem:441276" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=pubchem&id=441276" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=pubchem:441276" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=441276" | wc


curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=pfam:PF01756" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=pfam&id=PF01756" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=pfam:PF01756" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=PF01756" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=pfam:PF00091" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=pfam&id=PF00091" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=pfam:PF00091" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=PF00091" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=pfam:PF00110" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=pfam&id=PF00110" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=pfam:PF00110" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=PF00110" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=pfam:PF04901" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=pfam&id=PF04901" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=pfam:PF04901" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=PF04901" | wc


curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=taxonomy:3702" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=taxonomy&id=3702" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=taxonomy:3702" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=3702" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=taxonomy:10029" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=taxonomy&id=10029" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=taxonomy:10029" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=10029" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=taxonomy:36278" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=taxonomy&id=36278" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=taxonomy:36278" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=36278" | wc

curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=taxonomy:10116" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=taxonomy&id=10116" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=taxonomy:10116" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=10116" | wc


curl -s -XPOST "http://localhost:${PORT}/version"; echo ""

##### POSTs #####
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "uniprot:P31749"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "uniprot", "id": "P31749"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "uniprot:P31749"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "P31749"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "uniprot:Q99683"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "uniprot", "id": "Q99683"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "uniprot:Q99683"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "Q99683"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "uniprot:Q13131"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "uniprot", "id": "Q13131"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "uniprot:Q13131"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "Q13131"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "uniprot:Q02750"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "uniprot", "id": "Q02750"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "uniprot:Q02750"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "Q02750"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "uniprot:P36507"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "uniprot", "id": "P36507"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "uniprot:P36507"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "P36507"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "uniprot:P01116"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "uniprot", "id": "P01116"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "uniprot:P01116"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "P01116"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "uniprot:Q9P0J7"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "uniprot", "id": "Q9P0J7"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "uniprot:Q9P0J7"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "Q9P0J7"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "uniprot:P15056"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "uniprot", "id": "P15056"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "uniprot:P15056"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "P15056"}' -H "$PTYPE" | wc


curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "pubchem:5870"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "pubchem", "id": "5870"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "pubchem:5870"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "5870"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "pubchem:6830"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "pubchem", "id": "6830"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "pubchem:6830"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "6830"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "pubchem:16666708"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "pubchem", "id": "16666708"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "pubchem:16666708"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "16666708"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "pubchem:441276"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "pubchem", "id": "441276"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "pubchem:441276"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "441276"}' -H "$PTYPE" | wc


curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "pfam:PF01756"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "pfam", "id": "PF01756"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "pfam:PF01756"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "PF01756"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "pfam:PF00091"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "pfam", "id": "PF00091"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "pfam:PF00091"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "PF00091"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "pfam:PF00110"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "pfam", "id": "PF00110"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "pfam:PF00110"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "PF00110"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "pfam:PF04901"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "pfam", "id": "PF04901"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "pfam:PF04901"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "PF04901"}' -H "$PTYPE" | wc


curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "taxonomy:3702"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "taxonomy", "id": "3702"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "taxonomy:3702"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "3702"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "taxonomy:10029"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "taxonomy", "id": "10029"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "taxonomy:10029"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "10029"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "taxonomy:36278"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "taxonomy", "id": "36278"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "taxonomy:36278"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "36278"}' -H "$PTYPE" | wc

curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "taxonomy:10116"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "taxonomy", "id": "10116"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": ": "taxonomy:10116"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "10116"}' -H "$PTYPE" | wc


echo "Test ending..."
date
