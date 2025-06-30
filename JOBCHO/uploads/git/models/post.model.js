const db = require("../data/database");
const { ObjectId } = require("mongodb");

class Post {
  static async getAll() {
    return await db
      .getDb()
      .collection("posts")
      .find()
      .sort({ date: -1 })
      .toArray();
  }

  static async countAll() {
    return await db.getDb().collection("posts").countDocuments();
  }

  static async countByKind(kind) {
    return await db
      .getDb()
      .collection("posts")
      .countDocuments({ post_kind: kind });
    }
    static async countByAuthor(author) {
      return await db
        .getDb()
        .collection("posts")
        .countDocuments({ author });
    }

  static async getPaginated(page, perPage) {
    return await db
      .getDb()
      .collection("posts")
      .find()
      .sort({ date: -1 })
      .skip((page - 1) * perPage)
      .limit(perPage)
      .toArray();
  }

  static async getByKindPaginated(kind, page, perPage) {
    return await db
      .getDb()
      .collection("posts")
      .find({ post_kind: kind })
      .sort({ date: -1 })
      .skip((page - 1) * perPage)
      .limit(perPage)
      .toArray();
  }

  static async getByAuthorPaginated(author, page, perPage) {
    return await db
      .getDb()
      .collection("posts")
      .find({ author })
      .sort({ date: -1 })
      .skip((page - 1) * perPage) 
      .limit(perPage)
      .toArray();
  }

  static async getByKind(postKind) {
    return await db
      .getDb()
      .collection("posts")
      .find({ post_kind: postKind })
      .sort({ date: -1 })
      .toArray();
  }

  static async getById(postId) {
    const post = await db
      .getDb()
      .collection("posts")
      .findOne({ _id: new ObjectId(postId) });
  
    if (!post) return null;
  
    const authorProfile = await db
      .getDb()
      .collection("users")
      .findOne({ nickname: post.author }, { projection: { profileImg: 1 } });
  
    return { ...post, authorProfile: authorProfile ? authorProfile.profileImg : null };
  }
  

  static async incrementViews(postId) {
    return await db
      .getDb()
      .collection("posts")
      .updateOne({ _id: new ObjectId(postId) }, { $inc: { views: 1 } });
  }

  static async create(postData) {
    return await db.getDb().collection("posts").insertOne(postData);
  }

  static async findByAuthor(author) {
    return await db
      .getDb()
      .collection("posts")
      .find({ author })
      .sort({ date: -1 })
      .toArray();
  }
  static async updateLikes(postId, userId) {
    const post = await db
      .getDb()
      .collection("posts")
      .findOne({ _id: new ObjectId(postId) });

    if (!post) return null;

    const hasLiked = post.likedUsers?.includes(userId);
    const update = hasLiked
      ? { $pull: { likedUsers: userId }, $inc: { likes: -1 } }
      : { $addToSet: { likedUsers: userId }, $inc: { likes: 1 } };

    await db
      .getDb()
      .collection("posts")
      .updateOne({ _id: new ObjectId(postId) }, update);

    return { likes: post.likes + (hasLiked ? -1 : 1), liked: !hasLiked };
  }

  static async updateById(postId, updatedPost) {
    return await db.getDb().collection("posts").updateOne(
      { _id: new ObjectId(postId) },
      { $set: updatedPost }
    );
  }

  static async deleteById(postId) {
    return await db.getDb().collection("posts").deleteOne({ _id: new ObjectId(postId) });
  }  
}

module.exports = Post;
