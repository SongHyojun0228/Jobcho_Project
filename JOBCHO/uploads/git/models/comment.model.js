const db = require("../data/database");
const { ObjectId } = require("mongodb");

class Comment {
  static async getByPostId(postId) {
    return await db
      .getDb()
      .collection("comments")
      .find({ postId: new ObjectId(postId) })
      .toArray();
  }

  static async create(commentData) {
    return await db
      .getDb()
      .collection("comments")
      .insertOne({
        ...commentData,
        postId: new ObjectId(commentData.postId),
      });
  }

  static async countByPostId(postId) {
    return await db
      .getDb()
      .collection("comments")
      .countDocuments({ postId: new ObjectId(postId) });
  }

  static async findByPostId(postId) {
    const comments = await db
      .getDb()
      .collection("comments")
      .find({ postId: new ObjectId(postId) })
      .toArray();

    for (let comment of comments) {
      const user = await db
        .getDb()
        .collection("users")
        .findOne(
          { nickname: comment.author },
          { projection: { profileImg: 1 } }
        );

      comment.profileImg = user && user.profileImg ? user.profileImg : "/images/profile/default_profile.png";
    }

    return comments;
  }
}

module.exports = Comment;
