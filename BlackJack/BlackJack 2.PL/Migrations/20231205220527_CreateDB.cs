using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

#pragma warning disable CA1814 // Prefer jagged arrays over multidimensional

namespace BlackJack.PL.Migrations
{
    /// <inheritdoc />
    public partial class CreateDB : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "tblGames",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Status = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblGames", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "tblDecks",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    GameId = table.Column<Guid>(type: "uniqueidentifier", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblDecks", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tblDecks_tblGames_GameId",
                        column: x => x.GameId,
                        principalTable: "tblGames",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "tblUsers",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    GameId = table.Column<Guid>(type: "uniqueidentifier", nullable: true),
                    Nickname = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Username = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Password = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    IsComputerPlayer = table.Column<bool>(type: "bit", nullable: false),
                    Wins = table.Column<int>(type: "int", nullable: false),
                    Score = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblUsers", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tblUsers_tblGames_GameId",
                        column: x => x.GameId,
                        principalTable: "tblGames",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "tblCards",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Suit = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Rank = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Value = table.Column<int>(type: "int", nullable: false),
                    CardIMG = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    DeckId = table.Column<Guid>(type: "uniqueidentifier", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblCards", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tblCards_tblDecks_DeckId",
                        column: x => x.DeckId,
                        principalTable: "tblDecks",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "tblPlayerHands",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    UserId = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    CardId = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    tblUserId = table.Column<Guid>(type: "uniqueidentifier", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblPlayerHands", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tblPlayerHands_tblCards_CardId",
                        column: x => x.CardId,
                        principalTable: "tblCards",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_tblPlayerHands_tblUsers_tblUserId",
                        column: x => x.tblUserId,
                        principalTable: "tblUsers",
                        principalColumn: "Id");
                });

            migrationBuilder.InsertData(
                table: "tblGames",
                columns: new[] { "Id", "Status" },
                values: new object[,]
                {
                    { new Guid("0394e928-e4a5-4259-8966-22e5ceec478c"), true },
                    { new Guid("7b36ee30-6610-41d9-ada0-4d1754902f96"), false },
                    { new Guid("ae68511d-e1e3-4ebc-832d-9b2878d9854b"), true }
                });

            migrationBuilder.InsertData(
                table: "tblUsers",
                columns: new[] { "Id", "GameId", "IsComputerPlayer", "Nickname", "Password", "Score", "Username", "Wins" },
                values: new object[] { new Guid("6dc680ae-366d-4316-9cc2-4ba1b2caca84"), null, false, "Andy", "0DjJI+EtsxRM1PvfmpFRP6D9a6mraGmr0cEEK7vOQZ0=", 15, "Anthony", 14 });

            migrationBuilder.InsertData(
                table: "tblDecks",
                columns: new[] { "Id", "GameId" },
                values: new object[,]
                {
                    { new Guid("2d25ded1-dbd0-4a19-bf73-76fa858a850b"), new Guid("7b36ee30-6610-41d9-ada0-4d1754902f96") },
                    { new Guid("86b0cf81-ff6f-4303-a633-062f104fe124"), new Guid("0394e928-e4a5-4259-8966-22e5ceec478c") },
                    { new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), new Guid("ae68511d-e1e3-4ebc-832d-9b2878d9854b") }
                });

            migrationBuilder.InsertData(
                table: "tblUsers",
                columns: new[] { "Id", "GameId", "IsComputerPlayer", "Nickname", "Password", "Score", "Username", "Wins" },
                values: new object[,]
                {
                    { new Guid("609a2008-d017-485f-961d-7ef8d22c4695"), new Guid("ae68511d-e1e3-4ebc-832d-9b2878d9854b"), false, "Zach", "1TMdfaWrqALnYwE2LIDjXLBHAidQ+XZXxqZETIfbVwo=", 13, "Zachary", 1 },
                    { new Guid("d4ea645d-1a07-44ce-9345-e245e46a6f2f"), new Guid("ae68511d-e1e3-4ebc-832d-9b2878d9854b"), false, "Stef", "SerSsQZr2h4Sf2rgvRY3eNCFh+PpfZ7VjozJmXJGChw=", 12, "Stefan", 37 }
                });

            migrationBuilder.InsertData(
                table: "tblCards",
                columns: new[] { "Id", "CardIMG", "DeckId", "Rank", "Suit", "Value" },
                values: new object[,]
                {
                    { new Guid("02000ff8-5b1e-4989-9384-826f1e0c6c3a"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "10", "Diamonds", 10 },
                    { new Guid("08a281de-b031-4600-b94f-9abd93162861"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "Jack", "Diamonds", 10 },
                    { new Guid("0e37772a-04e6-46c1-ad5e-07a3f21a7348"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "10", "Hearts", 10 },
                    { new Guid("105af2a9-c9e5-41a4-8b8b-2b20626eac76"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "Jack", "Spades", 10 },
                    { new Guid("15b9df11-5be1-4f72-8a0f-a7250899a3f8"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "10", "Clubs", 10 },
                    { new Guid("1839df52-d8c8-4fd0-af7f-2fbe28d6344c"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "8", "Hearts", 8 },
                    { new Guid("1ab1a9cd-b681-4858-a0b1-2d650e7f12af"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "5", "Hearts", 5 },
                    { new Guid("1edf077b-b2c7-4215-93e8-cd8f09abbffd"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "Ace", "Spades", 1 },
                    { new Guid("1f23c4c2-3d52-437a-ab46-11637f5f28c9"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "8", "Clubs", 8 },
                    { new Guid("2da03c0e-86d2-49ba-9b58-0a71f2a1d5ed"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "Queen", "Clubs", 10 },
                    { new Guid("340b85e5-61b8-4478-b5ce-61c0bae38161"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "3", "Spades", 3 },
                    { new Guid("349cbb4c-1fb8-4f4c-a12d-a342de1c34b3"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "5", "Clubs", 5 },
                    { new Guid("36fcba80-f81b-4638-b6d8-f5454024eddc"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "3", "Diamonds", 3 },
                    { new Guid("39825b57-d33a-4dde-b7a0-1f0e33a60b4d"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "2", "Diamonds", 2 },
                    { new Guid("4b17d9fa-151f-4ddc-8381-2a28d834cbb7"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "Ace", "Clubs", 1 },
                    { new Guid("53c9e3ac-046a-476c-8de8-18d6f2a83d3a"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "6", "Clubs", 6 },
                    { new Guid("588aacd0-92f3-4526-9526-9e1a91fffed1"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "Ace", "Hearts", 1 },
                    { new Guid("59a01960-ab43-463d-b8d8-ac7efc93cc56"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "5", "Spades", 5 },
                    { new Guid("59dd4ba0-475d-4bb6-93e9-cd1472417539"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "2", "Spades", 2 },
                    { new Guid("62ad26ca-ba8d-4698-a70e-77fc484944f0"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "King", "Diamonds", 10 },
                    { new Guid("6789813c-9d29-493c-8b4d-78d4e9df14be"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "6", "Diamonds", 6 },
                    { new Guid("679044d2-de9b-4360-86fb-f4fce44d7281"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "Jack", "Clubs", 10 },
                    { new Guid("6dd04525-81fa-42da-a3d8-275c3968b8c6"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "6", "Hearts", 6 },
                    { new Guid("73af6a8a-37f1-4033-8c4a-8e349d6c3bdd"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "Ace", "Diamonds", 1 },
                    { new Guid("76a41734-5a6a-41ed-bef1-8c0ee34cdaa0"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "4", "Diamonds", 4 },
                    { new Guid("76bbd0de-7a0e-4d5f-b0d5-4558d13342f5"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "King", "Clubs", 10 },
                    { new Guid("7fcff96d-8ebb-4e88-99c9-99e1fe4d2eaa"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "3", "Hearts", 3 },
                    { new Guid("80f8b185-b671-4c68-a87d-a99f443ad3eb"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "King", "Spades", 10 },
                    { new Guid("9247de9b-d202-4a9b-a99d-df091624988a"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "Jack", "Hearts", 10 },
                    { new Guid("92cea1e9-8aaa-4feb-8422-0751eb71e35b"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "9", "Diamonds", 9 },
                    { new Guid("97d37fcf-d878-49f1-a73f-4353ba1ddd0b"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "9", "Hearts", 9 },
                    { new Guid("9f7ae0a1-a579-46bb-8cf5-b3a998a4837c"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "7", "Hearts", 7 },
                    { new Guid("a229bf64-3f3e-4d13-bf26-40822c137619"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "8", "Spades", 8 },
                    { new Guid("a4c591f4-6fee-4d9e-9d87-53871b15482a"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "Queen", "Hearts", 10 },
                    { new Guid("abe2ae18-7769-4a79-9760-f0ed5a1c7963"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "8", "Diamonds", 8 },
                    { new Guid("acf0f456-a63d-4220-b6c7-28c4c9110abb"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "10", "Spades", 10 },
                    { new Guid("aebce7a5-1010-4b1a-89d0-92ea5221cf3a"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "King", "Hearts", 10 },
                    { new Guid("af4fdae0-54c6-4735-8a1c-f5ceb89e5016"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "2", "Clubs", 2 },
                    { new Guid("b7545976-cd05-44a1-bbac-b9cf9e30d432"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "2", "Hearts", 2 },
                    { new Guid("c73c3c01-f3f5-427b-b1ef-44eaee24a8a3"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "7", "Spades", 7 },
                    { new Guid("cb3d9ce6-874d-4085-83ab-48a1952bbf9f"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "9", "Clubs", 9 },
                    { new Guid("d14f5c3f-3bfe-4a1f-94b2-bb678a1717c5"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "4", "Clubs", 4 },
                    { new Guid("d6923f35-f627-4c44-97f9-7e29d993ad19"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "7", "Clubs", 7 },
                    { new Guid("da05d94e-833d-4b4a-a52c-57309ad4cf03"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "3", "Clubs", 3 },
                    { new Guid("da3e6e93-692d-4624-ade7-8200d449821c"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "7", "Diamonds", 7 },
                    { new Guid("deee770f-a15c-465b-ab13-c2321493f7da"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "Queen", "Spades", 10 },
                    { new Guid("e03f362d-b793-4a8d-b870-ac4c4bc2e766"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "5", "Diamonds", 5 },
                    { new Guid("e3d2a657-5758-4933-8cd0-5e6b19c6b0f1"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "6", "Spades", 6 },
                    { new Guid("ef3399e1-5134-46e2-9de4-c39a0a3126e0"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "4", "Hearts", 4 },
                    { new Guid("f5bb7f8c-f5db-4d0c-8239-855914107599"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "4", "Spades", 4 },
                    { new Guid("f79078f3-e449-4b94-a5e2-dbc7dec90ebb"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "Queen", "Diamonds", 10 },
                    { new Guid("fc50fc01-91cf-4d71-9d20-1db664fa0bd7"), "", new Guid("e01c46df-21e8-4ea4-9697-731b428dee4a"), "9", "Spades", 9 }
                });

            migrationBuilder.InsertData(
                table: "tblPlayerHands",
                columns: new[] { "Id", "CardId", "UserId", "tblUserId" },
                values: new object[,]
                {
                    { new Guid("2b3f8e81-1dde-4d91-bd5d-45ba294e96d1"), new Guid("7fcff96d-8ebb-4e88-99c9-99e1fe4d2eaa"), new Guid("609a2008-d017-485f-961d-7ef8d22c4695"), null },
                    { new Guid("981f0ef7-3e06-4c7f-b70a-65495f17ef16"), new Guid("1ab1a9cd-b681-4858-a0b1-2d650e7f12af"), new Guid("d4ea645d-1a07-44ce-9345-e245e46a6f2f"), null },
                    { new Guid("ab5e9d6b-2dde-408c-8ff8-ca3495145295"), new Guid("1ab1a9cd-b681-4858-a0b1-2d650e7f12af"), new Guid("609a2008-d017-485f-961d-7ef8d22c4695"), null },
                    { new Guid("e3543e43-5c46-4f3f-8dca-ca36c2a10ec0"), new Guid("588aacd0-92f3-4526-9526-9e1a91fffed1"), new Guid("d4ea645d-1a07-44ce-9345-e245e46a6f2f"), null }
                });

            migrationBuilder.CreateIndex(
                name: "IX_tblCards_DeckId",
                table: "tblCards",
                column: "DeckId");

            migrationBuilder.CreateIndex(
                name: "IX_tblDecks_GameId",
                table: "tblDecks",
                column: "GameId");

            migrationBuilder.CreateIndex(
                name: "IX_tblPlayerHands_CardId",
                table: "tblPlayerHands",
                column: "CardId");

            migrationBuilder.CreateIndex(
                name: "IX_tblPlayerHands_tblUserId",
                table: "tblPlayerHands",
                column: "tblUserId");

            migrationBuilder.CreateIndex(
                name: "IX_tblUsers_GameId",
                table: "tblUsers",
                column: "GameId");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "tblPlayerHands");

            migrationBuilder.DropTable(
                name: "tblCards");

            migrationBuilder.DropTable(
                name: "tblUsers");

            migrationBuilder.DropTable(
                name: "tblDecks");

            migrationBuilder.DropTable(
                name: "tblGames");
        }
    }
}
