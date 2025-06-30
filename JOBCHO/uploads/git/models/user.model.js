const db = require("../data/database");
const bcrypt = require("bcrypt");

class User {
  constructor(email, password, name, nickname, date = null, visited = 0, addresses = []) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.nickname = nickname;
    this.date = date || new Date().toISOString().split("T")[0];
    this.visited = visited;
    this.addresses = addresses; 
  }

  async save() {
    const hashedPassword = await bcrypt.hash(this.password, 10); 
    return await db.getDb().collection("users").insertOne({
      email: this.email,
      password: hashedPassword,
      name: this.name,
      nickname: this.nickname,
      date: this.date,
      visited: this.visited,
      addresses: this.addresses,
    });
  }

  static async findByEmail(email) {
    return await db.getDb().collection("users").findOne({ email });
  }

  static async findByNickname(nickname) {
    return await db.getDb().collection("users").findOne({ nickname });
  }

  static async incrementVisit(nickname) {
    return await db
      .getDb()
      .collection("users")
      .updateOne({ nickname }, { $inc: { visited: 1 } });
  }

  static async verifyPassword(enteredPassword, hashedPassword) {
    return await bcrypt.compare(enteredPassword, hashedPassword);
  }

  static async updateProfileImg(nickname, profileImgUrl) {
    return await db
      .getDb()
      .collection("users")
      .updateOne({ nickname }, { $set: { profileImg: profileImgUrl } });
  }

  static async removeProfileImg(nickname) {
    return await db
      .getDb()
      .collection("users")
      .updateOne({ nickname }, { $unset: { profileImg: "" } });
  }
}

module.exports = User;
