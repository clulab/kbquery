#!/bin/sh
#
# Script with tests/examples to perform various (real) calls on the KBQuery server.
#
PTYPE='Content-Type:application/json'

echo "Test starting..."
date

##### GETs #####
curl -s 'http://localhost:8888/version'; echo ""
curl -s 'http://localhost:8888/countSources'; echo ""
curl -s 'http://localhost:8888/countEntries'; echo ""
curl -s 'http://localhost:8888/dumpSources' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=uniprot:P31749' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=uniprot:P31749' | wc
curl -s 'http://localhost:8888/kblu/byNsId?nsId=pubchem:5870' | wc
curl -s 'http://localhost:8888/kblu/synonyms?nsId=pubchem:5870' | wc
curl -s 'http://localhost:8888/kblu/byText?text=BLOOD' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=BLOOD' | wc
curl -s 'http://localhost:8888/kblu/byText?text=brown+fat' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=brown+fat' | wc
curl -s 'http://localhost:8888/kblu/byText?text=cell' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=cell' | wc
curl -s 'http://localhost:8888/kblu/byText?text=AKT2' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=AKT2' | wc
curl -s 'http://localhost:8888/kblu/byText?text=AKT1' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=AKT1' | wc
curl -s 'http://localhost:8888/kblu/byText?text=akt1' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=akt1' | wc
curl -s 'http://localhost:8888/kblu/byText?text=GTP' | wc
curl -s 'http://localhost:8888/kblu/lookup?text=GTP' | wc

##### POSTs #####
curl -s -XPOST 'http://localhost:8888/version'; echo ""
curl -s -XPOST 'http://localhost:8888/countSources'; echo ""
curl -s -XPOST 'http://localhost:8888/countEntries'; echo ""
curl -s -XPOST 'http://localhost:8888/dumpSources' | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId'   -d'{"nsId": "uniprot:P31749"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms' -d'{"nsId": "uniprot:P31749"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byNsId'   -d'{"nsId": "pubchem:5870"}'   -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/synonyms' -d'{"nsId": "pubchem:5870"}'   -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText'   -d'{"text": "BLOOD"}'     -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup'   -d'{"text": "BLOOD"}'     -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText'   -d'{"text": "brown fat"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup'   -d'{"text": "brown fat"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText'   -d'{"text": "cell"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup'   -d'{"text": "cell"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText'   -d'{"text": "AKT2"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup'   -d'{"text": "AKT2"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText'   -d'{"text": "AKT1"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup'   -d'{"text": "AKT1"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText'   -d'{"text": "akt1"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup'   -d'{"text": "akt1"}' -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/byText'   -d'{"text": "GTP"}'  -H "$PTYPE" | wc
curl -s -XPOST 'http://localhost:8888/kblu/lookup'   -d'{"text": "GTP"}'  -H "$PTYPE" | wc

echo "Test ending..."
date
