package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.consts.REGEX_SEED
import br.com.raphaelmaracaipe.core.data.sp.SeedSP
import com.github.curiousoddman.rgxgen.RgxGen
import javax.inject.Inject

class SeedRepositoryImpl @Inject constructor(
    private val seedSP: SeedSP
): SeedRepository {

    override fun cleanSeedSaved() {
        seedSP.clean()
    }

    override fun get() : String = seedSP.get().ifEmpty {
        val seedGenerated = RgxGen(REGEX_SEED).generate()
        seedSP.save(seedGenerated)
        seedGenerated
    }

}