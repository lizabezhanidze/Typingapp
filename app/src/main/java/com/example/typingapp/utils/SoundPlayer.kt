import android.content.Context
import android.media.MediaPlayer
import android.util.Log

object SoundPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun playSound(context: Context, soundResId: Int) {
        stopSound()

        try {
            mediaPlayer = MediaPlayer.create(context, soundResId).apply {
                setOnCompletionListener {
                    it.release()
                    mediaPlayer = null
                }
                start()
            }

            if (mediaPlayer == null) {
                Log.e("SoundPlayer", "Failed to create MediaPlayer")
            }
        } catch (e: Exception) {
            Log.e("SoundPlayer", "Error playing sound: ${e.message}")
        }
    }

    fun stopSound() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
