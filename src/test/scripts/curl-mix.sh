#!/bin/sh
#
# Script with tests/examples to perform various (real) calls on the KBQuery server.
#
PTYPE='Content-Type:application/json'
PORT=${1:-8888}

echo "Test starting..."
date

##### GETs #####
curl -s "http://localhost:${PORT}/version"; echo ""
curl -s "http://localhost:${PORT}/countEntries"; echo ""
curl -s "http://localhost:${PORT}/countKeys"; echo ""
curl -s "http://localhost:${PORT}/countLabels"; echo ""
curl -s "http://localhost:${PORT}/countNamespaces"; echo ""
curl -s "http://localhost:${PORT}/countSources"; echo ""
curl -s "http://localhost:${PORT}/dumpLabels" | wc
curl -s "http://localhost:${PORT}/dumpNamespaces" | wc
curl -s "http://localhost:${PORT}/dumpSources" | wc
curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=uniprot:P31749" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=uniprot:P31749" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=uniprot&id=P31749" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=P31749" | wc
curl -s "http://localhost:${PORT}/kblu/byNsId?nsId=pubchem:5870" | wc
curl -s "http://localhost:${PORT}/kblu/synonyms?nsId=pubchem:5870" | wc
curl -s "http://localhost:${PORT}/kblu/byNsAndId?ns=pubchem&id=5870" | wc
curl -s "http://localhost:${PORT}/kblu/byId?id=5870" | wc
curl -s "http://localhost:${PORT}/kblu/byText?text=BLOOD" | wc
curl -s "http://localhost:${PORT}/kblu/lookup?text=BLOOD" | wc
curl -s "http://localhost:${PORT}/kblu/byText?text=brown+fat" | wc
curl -s "http://localhost:${PORT}/kblu/lookup?text=brown+fat" | wc
curl -s "http://localhost:${PORT}/kblu/byText?text=cell" | wc
curl -s "http://localhost:${PORT}/kblu/lookup?text=cell" | wc
curl -s "http://localhost:${PORT}/kblu/byText?text=AKT2" | wc
curl -s "http://localhost:${PORT}/kblu/lookup?text=AKT2" | wc
curl -s "http://localhost:${PORT}/kblu/byText?text=AKT1" | wc
curl -s "http://localhost:${PORT}/kblu/lookup?text=AKT1" | wc
curl -s "http://localhost:${PORT}/kblu/byText?text=akt1" | wc
curl -s "http://localhost:${PORT}/kblu/lookup?text=akt1" | wc
curl -s "http://localhost:${PORT}/kblu/byText?text=GTP" | wc
curl -s "http://localhost:${PORT}/kblu/lookup?text=GTP" | wc

##### POSTs #####
curl -s -XPOST "http://localhost:${PORT}/version"; echo ""
curl -s -XPOST "http://localhost:${PORT}/countEntries"; echo ""
curl -s -XPOST "http://localhost:${PORT}/countKeys"; echo ""
curl -s -XPOST "http://localhost:${PORT}/countLabels"; echo ""
curl -s -XPOST "http://localhost:${PORT}/countNamespaces"; echo ""
curl -s -XPOST "http://localhost:${PORT}/countSources"; echo ""
curl -s -XPOST "http://localhost:${PORT}/dumpLabels" | wc
curl -s -XPOST "http://localhost:${PORT}/dumpNamespaces" | wc
curl -s -XPOST "http://localhost:${PORT}/dumpSources" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "uniprot:P31749"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "uniprot:P31749"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "uniprot", "id": "P31749"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"      -d'{"id": "P31749"}'           -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsId"    -d'{"nsId": "pubchem:5870"}'   -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/synonyms"  -d'{"nsId": "pubchem:5870"}'   -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byNsAndId" -d'{"ns": "pubchem", "id": "5870"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byId"     -d'{"id": "5870"}'        -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byText"   -d'{"text": "BLOOD"}'     -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/lookup"   -d'{"text": "BLOOD"}'     -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byText"   -d'{"text": "brown fat"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/lookup"   -d'{"text": "brown fat"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byText"   -d'{"text": "cell"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/lookup"   -d'{"text": "cell"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byText"   -d'{"text": "AKT2"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/lookup"   -d'{"text": "AKT2"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byText"   -d'{"text": "AKT1"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/lookup"   -d'{"text": "AKT1"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byText"   -d'{"text": "akt1"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/lookup"   -d'{"text": "akt1"}' -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/byText"   -d'{"text": "GTP"}'  -H "$PTYPE" | wc
curl -s -XPOST "http://localhost:${PORT}/kblu/lookup"   -d'{"text": "GTP"}'  -H "$PTYPE" | wc

echo "Test ending..."
date
