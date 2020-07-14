function connectWebSocket() {
    var websocket = new WebSocket("ws://localhost:9000/websocket");
    websocket.setTimeout

    websocket.onopen = function(event) {
        console.log("Connected to Websocket");
    }

    websocket.onclose = function () {
        console.log('Connection with Websocket Closed!');
        setTimeout(function () {
            connectWebSocket();
        }, 1000);

    };

    websocket.onerror = function (error) {
        console.log('Error in Websocket Occured: ' + error);
    };

    websocket.onmessage = function (e) {
        if (typeof e.data === "string") {
            let json = JSON.parse(e.data);
            game.fill(json);
            update(game);
            registerClickListener();
        }

    };
}

$(document).ready(function () {
    loadJson();
    connectWebSocket();
    $("#new").on("click", function() {
        return confirm('Sicher? Alle Fortschritte gehen verloren');
    });

});

$(document).ready(function () {
    loadJson()
    connectWebSocket();
    $("#giveup").on("click", function() {
        return confirm('Sicher? Alle Fortschritte gehen verloren');
    });

});

function registerClickListener() {
    let id;
    $(".block").click(function() {
        id = $(this).attr('id');
        console.log("id: " + id);
    });

    $(".cell").on("click", function() {
        var cellId = $(this).attr('id').split("/");
        var col = cellId[0];
        var row = cellId[1];
        $(".cell").off("click");
        $(".block").off("click");
            setBlock(id, col, row);
    });
}

class Blockpuzzle {
    constructor() {
        this.b1 = [];
        this.b2 = [];
        this.b3 = [];
        this.field = [];
        this.count = '';
        this.highscore= '';
        this.statusText= '';
    }

    fill(json) {
        this.count = json.count;
        this.highscore= json.highscore;
        this.statusText= json.statusText;

        var i, j, k, l;
        this.field = json.field.split("\n");
        for(i = 0; i < this.field.length; i++) {
            this.field[i] = this.field[i].split(" ");
        }


        if (this.b1 !== "") {
            this.b1 = json.b1.split(" \n");
            this.b1.pop();
            for(j = 0; j < this.b1.length; j++) {
                this.b1[j] = this.b1[j].split(" ");
            }
        }

        if (this.b2 !== "") {
            this.b2 = json.b2.split(" \n");
            this.b2.pop();
            for(k = 0; k < this.b2.length; k++) {
                this.b2[k] = this.b2[k].split(" ");
            }
        }

        if (this.b3 !== "") {
            this.b3 = json.b3.split(" \n");
            this.b3.pop();
            for(l = 0; l < this.b3.length; l++) {
                this.b3[l] = this.b3[l].split(" ");
            }
        }
    }
}

let game = new Blockpuzzle();

function loadJson() {
    $.ajax({
        method: "GET",
        url: "/json",
        dataType: "json",

        success: function (result) {
            game = new Blockpuzzle();
            game.fill(result);
            update(game);
            registerClickListener();
        }
    });
}

function setBlock(block, x, y) {
    $(".cell").off("click");
    $(".block").off("click");

    $.get("/add/" + block + "/" + x + "/" + y, function (data) {
        if(x>7 || x<0 || y<0 || y>7) {
            console.log("Error");
        } else {
            console.log("Set block " + block + " on " + x + "," + y);
        }
    });
}

function update(game) {
    $("#count").text("Punkte: " + game.count);
    $("#highscore").text("Highscore: " + game.highscore);
    $("#status").text("Letzte Aktion: " + game.statusText);

    $(".cell").each(function() {
        var id = $(this).attr('id').split("/");
        var col = parseInt(id[0]) - 1;
        var row = parseInt(id[1]) - 1;

        if (game.field[row][col] === '0') {
            $(this).children().attr('class', 'clear');
        } else {
            $(this).children().attr('class', 'set');
        }
    });

    $(".block").each(function() {
        var id = $(this).attr('id');
        $(this).empty();

        var block;
        if(id === '1') { block = game.b1;
        } else if(id === '2') { block = game.b2;
        } else { block = game.b3; }

        var i, j;
        for(i =  0; i < block.length; i++) {
            var cellRow = document.createElement('div');
            $(cellRow).addClass("cellRow");

            var blockSetBlock = block[i];

            for(j = 0; j < blockSetBlock.length; j++) {
                var blockCell = document.createElement('div');
                $(blockCell).addClass("blockCell");

                if(blockSetBlock[j] === '0') {
                    var blockClear = document.createElement('div');
                    $(blockClear).addClass("blockClear");
                    $(blockClear).appendTo(blockCell);
                } else {
                    var blockSet = document.createElement('div');
                    $(blockSet).addClass("blockSet");
                    $(blockSet).appendTo(blockCell);
                }

                $(blockCell).appendTo($(cellRow));
            }
            $(cellRow).appendTo($(this));
        }
    });
}

