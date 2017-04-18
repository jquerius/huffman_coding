/**
  * Created by zacharymikel on 4/17/17.
  */
class EqualityCheck {

  var statusMessage: String = ""

  def check(s: String, x: String): Boolean = {
    val sArray = s.toCharArray
    val xArray = x.toCharArray
    var i = 0
    while (i < s.length) {
      {
        if (sArray(i) != xArray(i)) {
          statusMessage = "Fail! Doesn't match at index " + i
          return false
        }
      }
      {
        i += 1; i - 1
      }
    }
    statusMessage = "Success! Files match."
    true
  }

  def getStatusMessage: String = { statusMessage }

}
