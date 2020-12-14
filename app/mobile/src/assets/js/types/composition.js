class Composition {
  compositionId;
  releaseDate;
  compositionBody;
  status;
  title;
  description;
  visibility;
  score;
  vaild;
  constructor({
    compositionId,
    releaseDate,
    compositionBody,
    status,
    title,
    description,
    visibility,
    score,
    vaild
  }) {
    this.compositionId = compositionId;
    this.releaseDate = releaseDate;
    this.compositionBody = compositionBody;
    this.status = status;
    this.title = title;
    this.description = description;
    this.visibility = visibility;
    this.score = score;
    this.vaild = vaild;
  }
}

export default Composition;
