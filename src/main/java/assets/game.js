var isSetup = true;
var placedShips = 0;
var game;
var shipType;
var sonar = false;
var numSonars = 0;
var maxShip = 4;
var playerTotalNumSink = 0;
var opponentTotalNumSink = 0;
var vertical;
var isSubView = false;

function makeGrid(table, isPlayer) {
    for (i=0; i<10; i++) {
        let row = document.createElement('tr');
        for (j=0; j<10; j++) {
            let column = document.createElement('td');
            column.addEventListener("click", cellClick);
            column.addEventListener("mouseup", cellDrop);
            row.appendChild(column);
        }
        table.appendChild(row);
    }
}

function markHits(board, elementId, surrenderText) {
    board.sonars.forEach((sonar) => {
        let Name;
        if (sonar.result === "EMPTY"){
            Name = "empty"
        }
        else if (sonar.result === "OCCUPIED"){
            Name = "occupied";
        }
        document.getElementById(elementId).rows[sonar.location.row-1].cells[sonar.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(Name);
    });
    board.attacks.forEach((attack) => {
        let className;
        if (attack.result === "MISS"){
            document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("captainQuarters");
            className = "miss";
            }
        else if (attack.result === "HIT")
            className = "hit";
        else if (attack.result === "SUNK"){
            document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("captainQuarters");
            className = "sink"
            }
        else if (attack.result === "SURRENDER"){
            className = "sink"
        }
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
    });
}

function refreshOpponentGrid(){
    sendXhr("POST","/refreshOpponent",{game:game, isFresh:true},function(data){
        game = data;
        redrawGrid();
    });
}

function redrawGrid() {
    if(isSubView){
        var boardP = game.playersSubBoard;
        var boardO = game.opponentsSubBoard;
    }
    else{
        var boardP = game.playersBoard;
        var boardO = game.opponentsBoard;
    }
    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    if (game === undefined) {
        return;
    }

    boardP.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));
    //Change the styling of player's captainquarters to green
    boardP.ships.forEach((ship) => {document.getElementById("player").rows[ship.captainQuarter.row-1].cells[ship.captainQuarter.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("occupied");
                                    document.getElementById("player").rows[ship.captainQuarter.row-1].cells[ship.captainQuarter.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("captainQuarters");
     });
    markHits(boardO, "opponent", "You won the game");


    if(game.opponentsSubBoard.numShipsSunk + game.opponentsBoard.numShipsSunk == maxShip){
        alert("You won the game");
    }
    markHits(boardP, "player", "You lost the game");
    if(game.playersSubBoard.numShipsSunk + game.playersBoard.numShipsSunk == maxShip){
            alert("You lost the game");
     }
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=0; i<10; i++) {
        for (j=0; j<10; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.removeEventListener("dragenter", oldListener);
            cell.removeEventListener("dragleave", oldListener)
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
            cell.addEventListener("dragenter", f);
            cell.addEventListener("dragleave",f);
        }
    }
    oldListener = f;
}

var isDragged = false;

function cellDrop(){
    if(isDragged){
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    if (isSetup) {
        sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical,isSubBoard:isSubView}, function(data) {
            game = data;
            redrawGrid();
            placedShips++;
            if (placedShips == maxShip) {
                isSetup = false;
                registerCellListener((e) => {});
            }
        });
    }
    isDragged = false;
    }
 }

function cellClick() {
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    if (isSetup) {
        sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical, isSubBoard:isSubView}, function(data) {
            game = data;
            //game.playersBoard.ships[placedShips]['@type'] = shipType.charAt(0) + shipType.slice(1).toLowerCase();
            //game.opponentsBoard.ships[placedShips]['@type'] = shipType.charAt(0) + shipType.slice(1).toLowerCase();
            redrawGrid();
            placedShips++;
            if (placedShips == maxShip) {
                isSetup = false;
                registerCellListener((e) => {});
            }
        });
    } else {
        if (sonar){
            if(numSonars < 2){
                sendXhr("POST", "/sonar", {game: game, x: row, y: col}, function(data) {
                    game = data;
                    redrawGrid();
                    numSonars++;
                });
            }
            sonar = false;
        }
        else{
            sendXhr("POST", "/attack", {game: game, x: row, y: col}, function(data) {
                game = data;
                //when player clicks attack,
                redrawGrid();
                //refreshOpponentGrid();
            });
        }
    }
}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
            alert("Cannot complete the action");
            return;
        }
        handler(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(data));
}

function place(size) {
    return function() {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        vertical = document.getElementById("is_vertical").checked;
        let table = document.getElementById("player");
        for (let i=0; i<size; i++) {
            let cell;
            if(vertical) {
                let tableRow = table.rows[row+i];
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    break;
                }
                cell = tableRow.cells[col];
            } else {
                cell = table.rows[row].cells[col+i];
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }

            cell.classList.toggle("placed");
            //cell.classList[size-2].toggle("captainQuarters");
        }
    }
}

document.getElementById("sonar_pulse").addEventListener("click",function(e) {
    sonar = true;
    });


function initGame() {
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    document.getElementById("place_minesweeper").addEventListener("click", function(e) {
        shipType = "MINESWEEPER";
       registerCellListener(place(2));
    });
    document.getElementById("place_destroyer").addEventListener("click", function(e) {
        shipType = "DESTROYER";
       registerCellListener(place(3));
    });
    document.getElementById("place_battleship").addEventListener("click", function(e) {
        shipType = "BATTLESHIP";
       registerCellListener(place(4));
    });
    document.getElementById("place_submarine").addEventListener("click", function(e) {
           shipType = "SUBMARINE";
           registerCellListener(place(4));
     });
    document.getElementById("place_minesweeper").addEventListener("dragstart", function(e) {
        e.preventDefault();
        isDragged = true;
        shipType = "MINESWEEPER";
        registerCellListener(place(2));
    });
    document.getElementById("place_destroyer").addEventListener("dragstart", function(e) {
        e.preventDefault();
        isDragged = true;
        shipType = "DESTROYER";
        registerCellListener(place(3));
    });
    document.getElementById("place_battleship").addEventListener("dragstart", function(e) {
        e.preventDefault();
        isDragged = true;
        shipType = "BATTLESHIP";
        registerCellListener(place(4));
    });
    document.getElementById("switch_boards").addEventListener("click",function(e){
        if(isSubView){
            document.getElementById("enemyBoardName").textContent = "Surface";
            document.getElementById("playerBoardName").textContent = "Surface";
        }
        else{
            document.getElementById("enemyBoardName").textContent = "Sub";
            document.getElementById("playerBoardName").textContent = "Sub";
        }
        isSubView = !isSubView;
        redrawGrid();
    });
    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });
};