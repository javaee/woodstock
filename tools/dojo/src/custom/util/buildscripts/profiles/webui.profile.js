dependencies = {
    layers:  [{
        name: "dojo.js",
        dependencies: [
            "dojo.i18n",
            "dijit._Widget",
            "dijit._Templated"
        ]}, 
        {
        name: "dnd.js",
        dependencies: [
            "dojo.dnd.Manager",
            "dojo.dnd.Source"
        ]}
    ],
    prefixes: [
        [ "dijit", "../dijit" ],
        [ "dojox", "../dojox" ]
    ]
};
